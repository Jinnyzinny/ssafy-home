<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <b class="orange">로그인</b>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form @submit.prevent="handleLogin">
        <div class="form-check mb-3 float-end">
          <input class="form-check-input" type="checkbox" id="saveid" v-model="saveId" />
          <label class="form-check-label" for="saveid"> 아이디저장 </label>
        </div>
        <div class="mb-3">
          <label for="userid" class="form-label">아이디 :</label>
          <input type="text" class="form-control" id="userid" v-model="userId" placeholder="아이디..." />
        </div>
        <div class="mb-3">
          <label for="userpwd" class="form-label">비밀번호 :</label>
          <input type="password" class="form-control" id="userpwd" v-model="userPwd" placeholder="비밀번호..." />
        </div>
        <div class="text-danger mb-2">{{ errorMessage }}</div>
        <div class="col-auto text-center">
          <button type="submit" class="btn btn-outline-primary mb-3">로그인</button>
          <button type="button" class="btn btn-outline-secondary mb-3" @click="goBack">뒤로가기</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: '',
      userPwd: '',
      saveId: false,
      errorMessage: ''
    };
  },
  methods: {
    async handleLogin() {
      if (!this.userId) {
        alert("아이디 입력!!");
        return;
      } else if (!this.userPwd) {
        alert("비밀번호 입력!!");
        return;
      }

      try {
        const response = await fetch('/user/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ userId: this.userId, userPwd: this.userPwd })
        });
        if (response.ok) {
          this.$router.push('/');
        } else {
          this.errorMessage = "Invalid credentials";
        }
      } catch (error) {
        this.errorMessage = "An error occurred: " + error.message;
      }
    },
    goBack() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.orange {
  color: orange;
}
</style>
