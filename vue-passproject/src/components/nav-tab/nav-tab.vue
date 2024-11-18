<script setup>
import { ref } from "vue";
import region from "./region.vue";
import interest from "./interest.vue";
import dustinfo from "./dustinfo.vue";
// 탭 관련 데이터
const activeTab = ref("region"); // 초기 활성화 탭
const tabs = [
  { name: "region", label: "지역선택", content: region },
  { name: "interest", label: "관심정보", content: interest },
  { name: "dust", label: "대기정보", content: dustinfo },
];
</script>

<template>
  <div id="sidebar">
    <!-- nav-tabs 구성 -->
    <ul class="nav nav-tabs" id="myTab" role="tablist">
      <li class="nav-item" role="presentation" v-for="(tab, index) in tabs" :key="index">
        <button
          class="nav-link"
          :class="{ active: activeTab === tab.name }"
          @click="activeTab = tab.name"
          type="button"
          role="tab"
          :aria-selected="activeTab === tab.name"
        >
          {{ tab.label }}
        </button>
      </li>
    </ul>

    <!-- tab-content 구성 -->
    <div class="tab-content" id="myTabContent">
      <div
        v-for="(tab, index) in tabs"
        :key="index"
        class="tab-pane fade"
        :class="{ 'show active': activeTab === tab.name }"
        role="tabpanel"
      >
        <component :is="tab.content" />
      </div>
    </div>
  </div>
</template>

<style scoped>
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
#search-results,
#selection-results {
  max-height: 400px;
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
</style>
