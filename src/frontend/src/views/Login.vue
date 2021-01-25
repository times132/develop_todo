<template>
    <b-container class="login-container">
        <div class="login-content">
            <div class="login-header">
                <h3>관리자 로그인</h3>
            </div>
            <form @submit.prevent="login(username, password)">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><b-icon class="fa" icon="person-fill"/></span>
                        <input type="text" class="form-control" v-model="username" placeholder="Username" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><b-icon class="fa" icon="lock-fill"/></span>
                        <input type="password" class="form-control" v-model="password" placeholder="Password" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block btn-lg">로그인</button>
                </div>
                <p class="hint-text"><a href="#">Forgot Password?</a></p>
            </form>
        </div>

    </b-container>
</template>

<script>
    export default {
        data() {
            return {
                username: '',
                password: '',
                msg: '',
                showDismissibleAlert: false
            }
        },
        methods: {
            login(username, password) {
                this.$store.dispatch('login', {username, password})
                    .then(() => {
                        this.$router.push(this.$route.query.redirect || '/')
                        // this.$router.go(-1)
                    })
                    .catch((status) => {
                        if (status === 401){
                            this.msg = "아이디나 비밀번호가 틀렸습니다."
                            this.makeToast('danger')
                        }
                    })
            },
            makeToast(variant) {
                this.$bvToast.toast('아이디나 비밀번호가 일치하지 않습니다.', {
                    title: '오류',
                    variant: variant,
                    solid: true
                })
            }
        }
    }
</script>

<style scoped>
    .login-container{
        max-width: 25rem;
    }
    .login-content{
        transform: translate(0, 40%);
    }
    .login-header{
        padding-bottom: 2rem;
    }
    .form-group{
        margin-bottom: 20px;
    }
    .form-group .input-group{
        position: relative;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        -ms-flex-align: stretch;
        align-items: stretch;
        width: 100%;
    }
    .form-group .input-group-addon{
        max-width: 42px;
        text-align: center;
        background: none;

    }
    .fa{
        font-size: 21px;
        position: relative;
        top: 6px;
    }
    .form-control{
        min-height: 38px;
        padding-left: 5px;
        box-shadow: none !important;
        border-width: 0 0 1px 0;
        border-radius: 0;
    }
    .form-control:focus{
        border-color: #0074D9;
    }
    .form-group .btn{
        font-size: 16px;
        font-weight: bold;
        background: #19aa8d !important;
        border-radius: 3px;
        border: none;
        min-width: 140px;
    }
    .form-group .btn:hover, form-group .btn:focus{
        background: #179b81!important;
    }
</style>