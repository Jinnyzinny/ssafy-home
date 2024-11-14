<script setup>
//axios로 User 정보를 얻는다
import axios from 'axios';
const getUserInfo = axios
  .get()
  .then((response) => {
    response.json();
  })
  .catch((error) => console.log(error));

//user 정보를 얻어서 있다면
</script>

<template>
  <div id="top-right-buttons">
    <button class="btn btn-custom info-btn" id="info-btn" onclick="location.href='${root}/board'">
      게시판
    </button>
    <c:choose>
      <c:when test="${user == null }">
        <button class="btn btn-custom" id="login-btn" data-toggle="modal" data-target="#loginModal">
          로그인
        </button>
        <button
          class="btn btn-custom"
          id="signup-btn"
          onclick="location.href='${root}/user/joinPage'"
        >
          회원가입
        </button>
      </c:when>
      <c:otherwise>
        <button class="btn" id="profile-btn" onclick="location.href='${root}/user/infoPage'">
          회원정보
        </button>
        <button class="btn" id="logout-btn" onclick="location.href='${root}/user/logout'">
          로그아웃
        </button>
      </c:otherwise>
    </c:choose>
  </div>
  <div
    class="modal fade"
    id="loginModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="loginModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="loginModalLabel">로그인</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form id="form-login" method="POST" action="${root}/user/login">
            <div class="form-check mb-3 float-end">
              <input
                class="form-check-input"
                type="checkbox"
                value="ok"
                id="saveid"
                name="saveid"
                ${idck}
              />
              <label class="form-check-label" for="saveid">아이디 저장</label>
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디:</label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userid"
                placeholder="아이디..."
                value="${saveid}"
              />
            </div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호:</label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userpwd"
                placeholder="비밀번호..."
              />
            </div>
            <c:if test="${error != null}">
              <div class="text-danger mb-2">${error}</div>
            </c:if>
            <div class="text-center">
              <button type="button" id="btn-login" class="btn btn-primary mb-3">로그인</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
</style>
