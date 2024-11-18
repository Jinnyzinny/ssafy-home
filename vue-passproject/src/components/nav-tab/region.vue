<script setup>
import { useMapStore } from "@/stores/mapStore";

const mapStore = useMapStore();

import { onMounted } from "vue";
onMounted(() => {
  initializeYearSelect();
  initializeSidoSelect();
  registerEventListeners();
});

const initializeSidoSelect = () => {
  sendRequest("sido-select", "*00000000");
};

function initializeYearSelect() {
  let date = new Date();
  let yearEl = document.querySelector("#year-select");
  let yearOpt = `<option value="">매매년도선택</option>`;
  let year = date.getFullYear();

  // 마지막 20년 동안의 연도 옵션을 생성
  for (let i = year; i > year - 20; i--) {
    yearOpt += `<option value="${i}">${i}년</option>`;
  }

  // 연도 선택 박스에 옵션 추가
  yearEl.innerHTML = yearOpt;

  // 연도가 변경될 때 월 선택 박스를 업데이트
  yearEl.addEventListener("change", handleYearChange);
}

function handleYearChange() {
  let date = new Date();
  let yearEl = document.querySelector("#year-select");
  let monthEl = document.querySelector("#month-select");
  let monthOpt = `<option value="">매매월선택</option>`;

  // 선택된 연도가 현재 연도인 경우, 현재 월까지만 옵션에 추가
  let selectedYear = parseInt(yearEl.value);
  let maxMonth = selectedYear === date.getFullYear() ? date.getMonth() + 1 : 12;

  // 월 선택 옵션 생성
  for (let i = 1; i <= maxMonth; i++) {
    let monthValue = i < 10 ? "0" + i : i; // 월 앞에 0을 추가
    monthOpt += `<option value="${monthValue}">${i}월</option>`;
  }

  // 월 선택 박스에 옵션 추가
  monthEl.innerHTML = monthOpt;
}

function registerEventListeners() {
  document
    .querySelector("#sido-select")
    .addEventListener("change", updateGugunSelect);
  document
    .querySelector("#gugun-select")
    .addEventListener("change", updateDongSelect);
  document
    .querySelector("#selection-search-btn")
    .addEventListener("click", searchAptDeals);
}

function updateGugunSelect() {
  const regcode = this.value.substr(0, 2) + "*00000";
  sendRequest("gugun-select", regcode);
}

function updateDongSelect() {
  const regcode = this.value.substr(0, 5) + "*";
  sendRequest("dong-select", regcode);
}

function sendRequest(selid, regcode) {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
  let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => addOption(selid, data));
}

function addOption(selid, data) {
  let opt = ``;
  initOption(selid);
  switch (selid) {
    case "sido-select":
      opt += `<option value="">시도선택</option>`;
      data.regcodes.forEach(function (regcode) {
        opt += `<option value="${regcode.code}">${regcode.name}</option>`;
      });
      break;
    case "gugun-select":
      opt += `<option value="">구군선택</option>`;
      data.regcodes.forEach(function (regcode) {
        let name =
          regcode.name.split(" ").length === 2
            ? regcode.name.split(" ")[1]
            : regcode.name.split(" ").slice(1).join(" ");
        opt += `<option value="${regcode.code}">${name}</option>`;
      });
      break;
    case "dong-select":
      opt += `<option value="">동선택</option>`;
      let idx = 2;
      data.regcodes.forEach(function (regcode) {
        if (regcode.name.split(" ").length !== 3) idx = 3;
        opt += `<option value="${regcode.code}">${
          regcode.name.split(" ")[idx]
        }</option>`;
      });
      break;
  }
  document.querySelector(`#${selid}`).innerHTML = opt;
}

function initOption(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
}

function searchAptDeals() {
  let gugun = document.querySelector("#gugun-select");
  let url =
    "https://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev";
  let regCode = gugun[gugun.selectedIndex].value.substr(0, 5);
  let yearSel = document.querySelector("#year-select");
  let year = yearSel[yearSel.selectedIndex].value;
  let monthSel = document.querySelector("#month-select");
  let month = monthSel[monthSel.selectedIndex].value;
  let dealYM = year + month;
  let queryParams =
    encodeURIComponent("serviceKey") +
    "=" +
    "DPvEhobzPWBOZeUPzkAk%2BrZ5QivehlbFnKUS%2FMZd8Owx%2BNA5PKwRI20j9YzN9qo0M6K3dDzS3deSt%2Fl6zFVjpg%3D%3D";
  queryParams +=
    "&" +
    encodeURIComponent("LAWD_CD") +
    "=" +
    encodeURIComponent(regCode); /*구군 코드*/
  queryParams +=
    "&" +
    encodeURIComponent("DEAL_YMD") +
    "=" +
    encodeURIComponent(dealYM); /*조회년월*/
  queryParams +=
    "&" +
    encodeURIComponent("pageNo") +
    "=" +
    encodeURIComponent("1"); /*페이지번호*/
  queryParams +=
    "&" +
    encodeURIComponent("numOfRows") +
    "=" +
    encodeURIComponent("30"); /*페이지당 건수*/

  fetch(`${url}?${queryParams}`)
    .then((response) => response.text())
    .then((data) => makeList(data));
  // const sido = document.querySelector("#sido-select").value;
  // const gugun = document.querySelector("#gugun-select").value;
  // const dong = document.querySelector("#dong-select").value;
  // console.log("시도" + sido);

  // fetch(`/house/searchAptDeals`, {
  // 	method: "POST",
  // 	headers: {
  // 		"Content-Type": "application/json",
  // 	},
  // 	body: JSON.stringify(data),
  // })
  // 	.then(response => response.json())
  // 	.then(text => console.log(text))
  //		.then(data => makeList(data))
}

function makeList(data) {
  let tbody = document.querySelector("#aptlist");
  tbody.innerHTML = ""; // 기존 데이터를 초기화

  let parser = new DOMParser();
  const xml = parser.parseFromString(data, "application/xml");
  console.log(xml); // XML 데이터 확인

  let apts = xml.querySelectorAll("item");

  if (apts.length === 0) {
    console.log("아파트 데이터가 없습니다.");
  }

  apts.forEach((apt) => {
    let tr = document.createElement("tr");

    let nameTd = document.createElement("td");
    nameTd.textContent = apt.querySelector("aptNm").textContent;
    tr.appendChild(nameTd);

    let floorTd = document.createElement("td");
    floorTd.textContent = apt.querySelector("floor").textContent;
    tr.appendChild(floorTd);

    let areaTd = document.createElement("td");
    areaTd.textContent = apt.querySelector("excluUseAr").textContent;
    tr.appendChild(areaTd);

    let dongTd = document.createElement("td");
    dongTd.textContent = apt.querySelector("umdNm").textContent;
    tr.appendChild(dongTd);

    let priceTd = document.createElement("td");
    priceTd.textContent =
      getEok(apt.querySelector("dealAmount").textContent) + "억원";
    priceTd.classList.add("text-end");
    tr.appendChild(priceTd);

    // Add a click event to show the location on the map
    tr.addEventListener("click", function () {
      const aptName = apt.querySelector("aptNm").textContent;
      const dealAmount = getEok(apt.querySelector("dealAmount").textContent);
      const address = `${apt.querySelector("umdNm").textContent} ${
        apt.querySelector("jibun").textContent
      }`;
      console.log("Event check");
      console.log(aptName, dealAmount, address);

      // 지오코딩을 통해 주소를 좌표로 변환
      const geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(address, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
          const position = {
            lat: parseFloat(result[0].y),
            lng: parseFloat(result[0].x),
          };

          // Pinia 상태 업데이트
          console.log(position.lat + " " + position.lng);
          mapStore.setAptDetails(aptName, dealAmount, address, position);
        } else {
          console.error("Geocoding failed:", status);
        }
      });
    });

    tbody.appendChild(tr);
  });

  // 테이블을 보이게 설정
  document.querySelector("table").style.display = "table";
}
function viewMap(apt, dealAmount, address) {
  // 기존 마커 없애기
  if (marker != null) {
    marker.setMap(null);
  }

  // 기존 infowindow 없애기
  if (infowindow != null) {
    infowindow.close();
  }

  // 기존 overlays 없애기
  if (overlays.length != 0) {
    overlays.forEach((overlay) => {
      overlay.setMap(null);
    });
    overlays = [];
  }

  // 주소-좌표 변환 객체를 생성합니다
  var geocoder = new kakao.maps.services.Geocoder();

  // 주소로 좌표를 검색합니다
  geocoder.addressSearch(address, async function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      // 결과값으로 받은 위치를 마커로 표시합니다
      marker = new kakao.maps.Marker({
        map: map,
        position: coords,
      });

      // 인포윈도우로 장소에 대한 설명을 표시합니다
      infowindow = new kakao.maps.InfoWindow({
        content: `<div style="width:150px;text-align:center;padding:6px 0;">${apt}, ${dealAmount}억원</div>`,
        zIndex: 100,
      });
      infowindow.open(map, marker);

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      await map.setCenter(coords);

      // 근처 업장 정보를 최신화 시키고 마커로 표시합니다.
      const businessRows = document.querySelectorAll("#businessinfolist tr");

      // 근처 업장 테이블의 tr 별로 파싱합니다.
      let list = []; // 업장과 클릭한 아파트 사이에 거리를 담고 있는 리스트

      let promises = Array.from(businessRows).map((row) => {
        const cells = Array.from(row.querySelectorAll("td"));
        const rowData = cells.map((cell) => cell.textContent);

        const businessName = rowData[0];
        const industry = rowData[1];
        const address = rowData[2];

        // Promise를 반환하여 비동기 작업 완료를 보장
        return new Promise((resolve, reject) => {
          // 업장의 주소 정보를 좌표로 변환합니다.
          geocoder.addressSearch(address, (result, status) => {
            if (status == kakao.maps.services.Status.OK) {
              let bCoords = new kakao.maps.LatLng(result[0].y, result[0].x);

              // 업종별 가장 가까운 업장을 판별합니다.
              let deltaX = coords.getLat() - bCoords.getLat();
              let deltaY = coords.getLng() - bCoords.getLng();

              let dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

              list.push({ businessName, industry, bCoords, dist });
              resolve(); // 작업이 완료되면 resolve 호출
            } else {
              reject(status); // 에러가 발생하면 reject 호출
            }
          });
        });
      });

      // 모든 비동기 작업이 완료된 후 정렬을 실행합니다
      Promise.all(promises)
        .then(async () => {
          await selectionSort(list);
          //list.sort((a, b) => a.dist - b.dist);

          for (let idx = 0; idx < 10; idx++) {
            const ele = list[idx];
            console.log(ele);

            // overlay 띄우기
            let overlay = new kakao.maps.CustomOverlay({
              map: map,
              position: ele.bCoords,
              content: `<span style="display: block;background: #50627F;color: #fff;text-align: center;border-radius:4px;padding:0px 10px;">${ele.businessName}</span>`,
            });

            overlays.push(overlay);
          }
        })
        .catch((error) => {
          console.error("에러 발생:", error);
        });
    }
  });

  function selectionSort(arr) {
    return new Promise((resolve, reject) => {
      let n = arr.length;

      for (let i = 0; i < n - 1; i++) {
        let minIndex = i;
        for (let j = i + 1; j < n; j++) {
          if (arr[j].dist < arr[minIndex].dist) {
            minIndex = j;
          }
        }

        if (minIndex !== i) {
          let temp = arr[i];
          arr[i] = arr[minIndex];
          arr[minIndex] = temp;
        }
      }
      resolve();
    });
  }
}

function getEok(price) {
  var newprice = parseFloat(price.replace(",", "")) / 10000;
  return newprice;
}
</script>

<template>
  <div
    class="tab-pane fade show active"
    id="select"
    role="tabpanel"
    aria-labelledby="select-tab"
  >
    <h4>지역 선택</h4>
    <form
      id="selection-form"
      action="${root}/addFavoriteArea"
      method="post"
    >
      <div class="form-group">
        <label for="sido-select">시도 선택</label>
        <select
          class="form-control"
          id="sido-select"
          name="province"
        ></select>
      </div>
      <div class="form-group">
        <label for="gugun-select">구군 선택</label>
        <select
          class="form-control"
          id="gugun-select"
          name="city"
        >
          <option value="">구군선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="dong-select">동 선택</label>
        <select
          class="form-control"
          id="dong-select"
          name="dong"
        >
          <option value="">동선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="year-select">연도 선택</label>
        <select
          class="form-control"
          id="year-select"
          name="year"
        >
          <option value="">연도선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="month-select">월 선택</label>
        <select
          class="form-control"
          id="month-select"
          name="month"
        >
          <option value="">월선택</option>
        </select>
      </div>
      <input
        type="hidden"
        id="latitude"
        name="latitude"
      />
      <input
        type="hidden"
        id="longitude"
        name="longitude"
      />
      <div class="mb-4 text-right">
        <button
          type="button"
          class="btn btn-primary"
          id="selection-search-btn"
          formaction="${root}/house/searchAptDeals"
          formmethod="POST"
        >
          검색
        </button>
        <button
          type="submit"
          class="btn btn-primary"
          id="areaAddBtn"
          formaction="${root}/interest/addFavoriteArea"
        >
          관심 지역으로 추가
        </button>
      </div>
    </form>

    <div id="selection-results">
      <table
        class="table table-hover text-center"
        style="display: none"
      >
        <tr>
          <th>아파트이름</th>
          <th>층</th>
          <th>면적</th>
          <th>법정동</th>
          <th>거래금액</th>
        </tr>
        <tbody id="aptlist"></tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
