<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const coordinate = {
  lat: 33.450701,
  lng: 126.570667,
};
</script>

<template>
  <KakaoMap
    id="map"
    width="100%"
    height="100vh"
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :draggable="true"
  >
    <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
  </KakaoMap>
  <div id="sidebar">
    <!-- 탭 네비게이션 -->
    <ul class="nav nav-tabs" id="myTab" role="tablist">
      <li class="nav-item">
        <a
          class="nav-link active"
          id="select-tab"
          data-toggle="tab"
          href="#select"
          role="tab"
          aria-controls="select"
          aria-selected="false"
          >지역 선택</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          id="search-tab"
          data-toggle="tab"
          href="#search"
          role="tab"
          aria-controls="search"
          aria-selected="true"
          >관심 지역</a
        >
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          id="dustinfo-tab"
          data-toggle="tab"
          href="#dustinfo"
          role="tab"
          aria-controls="dustinfo"
          aria-selected="true"
          >대기 정보</a
        >
      </li>
      <!-- <li class="nav-item"><a class="nav-link" id="businessinfo-tab"
				data-toggle="tab" href="#businessinfo" role="tab"
				aria-controls="businessinfo" aria-selected="true">업장 정보</a></li> -->
    </ul>
    <div class="tab-content" id="myTabContent">
      <!-- 첫 번째 탭: 시도, 구군, 동 선택 -->
      <div
        class="tab-pane fade show active"
        id="select"
        role="tabpanel"
        aria-labelledby="select-tab"
      >
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
      <!-- 두 번째 탭: 관심 지역 -->
      <div class="tab-pane fade" id="search" role="tabpanel" aria-labelledby="search-tab">
        <h4>관심 지역</h4>
        <div class="container my-5">
          <table class="table table-bordered">
            <thead></thead>
            <tbody id="fav-list"></tbody>
          </table>
          <button class="btn btn-primary" onclick="fetchInterestInfo()">관심 지역 정보 조회</button>
        </div>
      </div>
      <!-- 세 번째 탭: 아파트 정보 -->
			<div class="tab-pane fade" id="aptinfo" role="tabpanel"
				aria-labelledby="search-tab">
				<h4>아파트 정보</h4>
				<div class="container my-5">
					<table class="table table-hover text-center">
						<tr>
							<th>아파트이름</th>
							<th>층</th>
							<th>면적</th>
							<th>법정동</th>
							<th>거래금액</th>
						</tr>
						<tbody id="aptinfolist"></tbody>
					</table>
				</div>
			</div>
      <!-- 네 번째 탭: 대기 정보 -->
			<div class="tab-pane fade" id="dustinfo" role="tabpanel"
				aria-labelledby="search-tab">
				<h4>대기 정보</h4>
				<div class="container my-5">
					<table class="table table-hover text-center">
						<tr>
							<th>번호</th>
							<th>군/구</th>
							<th>등급</th>
						</tr>
						<tbody id="dustinfolist"></tbody>
					</table>
					<button class="btn btn-primary" onclick="fetchDustInfo()">대기
						정보 조회</button>
				</div>
			</div>
    </div>
  </div>
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
#sidebar {
  position: absolute;
  left: 20px;
  top: 20px;
  bottom: 20px;
  width: 450px; /* Increase sidebar width */
  background-color: rgba(255, 255, 255, 0.8);
  color: black;
  padding: 15px;
  z-index: 2;
  border-radius: 8px;
  overflow-y: auto;
}
.tab-content {
  margin-top: 20px;
}

#search-results,
#selection-results {
  max-height: 300px;
  overflow-y: auto;
  margin-top: 20px;
}

#selection-results .table {
  font-size: 12px;
}

#selection-results .table th:nth-child(1),
#selection-results .table td:nth-child(1) {
  width: 200px;
}

#selection-results .table th:nth-child(2),
#selection-results .table td:nth-child(2) {
  width: 70px;
}

#selection-results .table th:nth-child(3),
#selection-results .table td:nth-child(3) {
  width: 70px;
}

#selection-results .table th:nth-child(4),
#selection-results .table td:nth-child(4) {
  width: 100px; /* Increase width for 법정동 */
}

#selection-results .table th:nth-child(5),
#selection-results .table td:nth-child(5) {
  width: 200px; /* Increase width for 거래금액 */
}

#top-right-buttons {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 3;
  display: flex;
}

.btn-custom {
  background-color: rgba(0, 123, 255, 0.9);
  color: white;
  margin-left: 10px;
}

.btn-custom:first-child {
  margin-left: 0;
}

.btn-custom.info-btn {
  background-color: yellow;
  color: black;
}

#profile-btn {
  background-color: #6c757d;
  color: white;
  margin-right: 10px;
  margin-left: 10px;
}

#signup-btn {
  background-color: #28a745;
  color: white;
}

#logout-btn {
  background-color: #dc3545;
  color: white;
}

#signup-info-btn {
  background-color: #e0e0e0; /* Slightly darker than white */
  color: black;
  margin-left: 10px;
}

#search-form,
#selection-form {
  display: flex;
  flex-direction: column;
}

#search-input {
  flex: 1;
  margin-bottom: 10px;
}

.form-group {
  margin-bottom: 10px;
}

#header_nav_confirm_on {
  display: none;
}

.btn-find-password {
  background-color: #28a745;
  color: white;
  margin-right: 10px;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
}

.modal-footer .btn-danger {
  margin-left: 10px;
}

#bottom-right-buttons {
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 3;
  display: flex;
  flex-direction: row; /* 한 줄에 배치되도록 수정 */
}

#bottom-right-buttons .hospital-btn {
  background-color: #14d04a; /* 민트색 */
  color: white;
  margin-right: 10px; /* 버튼 사이에 간격 추가 */
}

#bottom-right-buttons .subway-btn {
  background-color: #6f42c1; /* 보라색 */
  color: white;
}
</style>
