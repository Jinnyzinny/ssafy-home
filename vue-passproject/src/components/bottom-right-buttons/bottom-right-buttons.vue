<script setup></script>

<template>
  <div id="bottom-right-buttons">
    <button class="btn btn-custom hospital-btn" onclick="showHospital()">병원</button>
    <button class="btn btn-custom subway-btn" onclick="showSubways()">지하철</button>
  </div>
</template>

<style scoped>
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
