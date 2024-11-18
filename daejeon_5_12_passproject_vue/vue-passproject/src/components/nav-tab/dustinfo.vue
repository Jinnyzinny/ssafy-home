<script setup></script>

<template>
  <div class="tab-pane fade show info" id="dustinfo" role="tabpanel" aria-labelledby="search-tab">
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
      <button class="btn btn-primary" onclick="fetchDustInfo()">대기 정보 조회</button>
    </div>
  </div>
</template>

<style scoped></style>
