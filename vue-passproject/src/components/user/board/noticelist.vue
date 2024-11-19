<template>
  <div v-if="isVisible" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <!-- 헤더 섹션 -->
      <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <b class="sky">게시판</b>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <!-- 글쓰기 버튼과 검색 폼 -->
                <div class="row align-self-center mb-2">
                    <div class="col-md-2 text-start">
                        <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">글쓰기</button>
                    </div>
                    <div class="col-md-7 offset-3">
                        <form class="d-flex" id="form-search">
                            <select id="key" class="form-select form-select-sm ms-5 me-1 w-50" aria-label="검색조건">
                                <option value="">검색조건</option>
                                <option value="article_no">글번호</option>
                                <option value="subject">제목</option>
                                <option value="user_id">작성자</option>
                            </select>
                            <div class="input-group input-group-sm">
                                <input type="text" id="word" class="form-control" placeholder="검색어...">
                                <button id="btn-search" class="btn btn-dark" type="button">검색</button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- 게시글 테이블 -->
                <table class="table table-hover">
                    <thead>
                        <tr class="text-center">
                            <th scope="col">글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody id="article-list">
                        <!-- 게시글 목록이 동적으로 추가됩니다 -->
                    </tbody>
                </table>
                <!-- 페이지네이션 -->
                <div class="row justify-content-center" id="pagination">
                    <!-- 페이지네이션 버튼들이 동적으로 추가됩니다 -->
                </div>

                <button type="button" class="btn btn-outline-primary btn-sm" onclick="location.href='/'">홈페이지</button>
            </div>
        </div>
      <button class="close-button" @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

// 부모로부터 받을 props 정의
defineProps({
  isVisible: {
    type: Boolean,
    required: true,
  },
});

// 부모에게 이벤트를 전달
const emit = defineEmits(["close"]);

const closeModal = () => {
  emit("close");
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
    width: 90%;
  background: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 300px;
}

.close-button {
  margin-top: 10px;
  padding: 5px 10px;
  border: none;
  background-color: #ff5f5f;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
</style>
