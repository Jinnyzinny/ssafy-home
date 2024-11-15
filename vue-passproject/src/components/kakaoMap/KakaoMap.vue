<script setup>
import bottom_right_buttons from "@/components/bottom-right-buttons/bottom-right-buttons.vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const coordinate = {
  lat: 33.450701,
  lng: 126.570667,
<<<<<<< Updated upstream
};
=======
}; //기본 좌표 현재는 카카오 제주 본사로 되어있다.
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
>>>>>>> Stashed changes
</script>

<template>
  <KakaoMap
    id="map"
    width="100%"
    height="100vh"
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :draggable="true"
    z-index="0"
  >
    <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
  </KakaoMap>

  <bottom_right_buttons /><!--이 부분이 하단 병원 지하철 버튼의 구현부-->
</template>
<style scoped>
#map {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
}
</style>
