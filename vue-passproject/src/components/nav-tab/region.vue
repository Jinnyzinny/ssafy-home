<script setup>
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
  document.querySelector("#sido-select").addEventListener("change", updateGugunSelect);
  document.querySelector("#gugun-select").addEventListener("change", updateDongSelect);
  document.querySelector("#selection-search-btn").addEventListener("click", searchAptDeals);
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
        opt += `<option value="${regcode.code}">${regcode.name.split(" ")[idx]}</option>`;
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
  let url = "https://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev";
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
    "&" + encodeURIComponent("LAWD_CD") + "=" + encodeURIComponent(regCode); /*구군 코드*/
  queryParams +=
    "&" + encodeURIComponent("DEAL_YMD") + "=" + encodeURIComponent(dealYM); /*조회년월*/
  queryParams += "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1"); /*페이지번호*/
  queryParams +=
    "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("30"); /*페이지당 건수*/

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
    priceTd.textContent = getEok(apt.querySelector("dealAmount").textContent) + "억원";
    priceTd.classList.add("text-end");
    tr.appendChild(priceTd);

    // Add a click event to show the location on the map
    tr.addEventListener("click", function () {
      const aptName = apt.querySelector("aptNm").textContent;
      const dealAmount = getEok(apt.querySelector("dealAmount").textContent);
      const address = `${apt.querySelector("umdNm").textContent} ${
        apt.querySelector("jibun").textContent
      }`;

      viewMap(aptName, dealAmount, address);
    });

    tbody.appendChild(tr);
  });

  // 테이블을 보이게 설정
  document.querySelector("table").style.display = "table";
}
// const viewMap = (apt, dealAmount, addresss) => {
//   const geoCoder = new kakao.maps.services.Geocoder();

//   geoCoder.addressSearch(address, async function (result, status) {
//     if (status === kakao.maps.services.Status.OK) {
//       const coords = new kakao.maps.LatLng();

//       const marker = new kakao.maps.Marker({
//         map: map,
//         position: coords,
//       });

//       const infowindow = new kakao.maps.InfoWindow({
//         content: `<div style="width:150px;text-align:center;padding:6px 0;">${apt}, ${dealAmount}억원</div>`,
//         zIndex: 10,
//       });
//       infowindow.open(map, marker);

//       map.setCenter(coords); /*여기 지금 문제 있음*/
//     }
//   });
// };

function getEok(price) {
  var newprice = parseFloat(price.replace(",", "")) / 10000;
  return newprice;
}
</script>

<template>
  <div class="tab-pane fade show active" id="select" role="tabpanel" aria-labelledby="select-tab">
    <h4>지역 선택</h4>
    <form id="selection-form" action="${root}/addFavoriteArea" method="post">
      <div class="form-group">
        <label for="sido-select">시도 선택</label>
        <select class="form-control" id="sido-select" name="province"></select>
      </div>
      <div class="form-group">
        <label for="gugun-select">구군 선택</label>
        <select class="form-control" id="gugun-select" name="city">
          <option value="">구군선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="dong-select">동 선택</label>
        <select class="form-control" id="dong-select" name="dong">
          <option value="">동선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="year-select">연도 선택</label>
        <select class="form-control" id="year-select" name="year">
          <option value="">연도선택</option>
        </select>
      </div>
      <div class="form-group">
        <label for="month-select">월 선택</label>
        <select class="form-control" id="month-select" name="month">
          <option value="">월선택</option>
        </select>
      </div>
      <input type="hidden" id="latitude" name="latitude" />
      <input type="hidden" id="longitude" name="longitude" />
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
      <table class="table table-hover text-center" style="display: none">
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
