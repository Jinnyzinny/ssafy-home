<script setup></script>

<template>
  <div class="tab-pane fade show active" id="search" role="tabpanel" aria-labelledby="search-tab">
    <h4>관심 지역</h4>
    <div class="container my-5">
      <table class="table table-bordered">
        <thead></thead>
        <tbody id="fav-list"></tbody>
      </table>
      <button class="btn btn-primary" onclick="fetchInterestInfo()">관심 지역 정보 조회</button>
    </div>
  </div>
</template>

<style scoped></style>
