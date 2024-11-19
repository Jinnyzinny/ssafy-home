<script setup>

</script>

<template>
    <div>
        <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <b class="sky">글쓰기</b>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <form id="form-register">
                    <div class="mb-3">
                        <label for="subject" class="form-label">제목 : </label>
                        <input type="text" class="form-control" id="subject" name="subject" placeholder="제목..." />
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label">내용 : </label>
                        <textarea class="form-control" id="content" name="content" rows="7"></textarea>
                    </div>
                    <div class="col-auto text-center">
                        <button type="button" id="btn-register" class="btn btn-outline-primary mb-3">글작성</button>
                        <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
                        <button type="button" id="btn-back" class="btn btn-outline-secondary mb-3" onclick="location.href='/board/list.html'">뒤로가기</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    </div>
</template>

<style scoped>

</style>