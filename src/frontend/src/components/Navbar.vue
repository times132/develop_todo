<template v-slot:header>
    <div>
        <b-navbar toggleable="md">
            <div class="container">
                <!-- logo and name -->
                <router-link class="company navbar-brand" to="/">중고쇼핑몰</router-link>
                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
                <!-- navbar -->
                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav class="categoryItem">

                        <b-nav-item-dropdown class="nav-list" text="에어컨" no-caret>
                            <b-dropdown-item to="/goods/011">스탠드</b-dropdown-item>
                            <b-dropdown-item to="/goods/012">중대형</b-dropdown-item>
                            <b-dropdown-item to="/goods/013">벽걸이</b-dropdown-item>
                        </b-nav-item-dropdown>

                        <!-- 가전 -->
                        <b-nav-item-dropdown class="nav-list" text="생활가전" no-caret>
                            <b-dropdown-item to="/goods/021">냉장고</b-dropdown-item>
                            <b-dropdown-item to="/goods/022">세탁기</b-dropdown-item>
                            <b-dropdown-item to="/goods/023">TV</b-dropdown-item>
                        </b-nav-item-dropdown>

                        <!-- 업소용 -->
                        <b-nav-item-dropdown class="nav-list" text="업소용" no-caret>
                            <b-dropdown-item to="/goods/031">선반</b-dropdown-item>
                            <b-dropdown-item to="/goods/032">냉장/냉동고</b-dropdown-item>
                            <b-dropdown-item to="/goods/033">화구</b-dropdown-item>
                        </b-nav-item-dropdown>

                        <!-- 가구 -->
                        <b-nav-item-dropdown class="nav-list" text="사무용" no-caret>
                            <b-dropdown-item to="/goods/041">사무용</b-dropdown-item>
                            <b-dropdown-item to="/goods/042">사무용2</b-dropdown-item>
                            <b-dropdown-item to="/goods/043">사무용3</b-dropdown-item>
                        </b-nav-item-dropdown>

                        <!-- 기타 게시판 -->
                        <b-nav-item-dropdown class="nav-list" text="기타" no-caret>
                            <b-dropdown-item to="/free">자유게시판</b-dropdown-item>
                            <b-dropdown-item to="/ask">문의하기</b-dropdown-item>
                            <b-dropdown-item to="/notice">공지사항</b-dropdown-item>
                        </b-nav-item-dropdown>
                    </b-navbar-nav>

                    <b-navbar-nav class="mr-auto">
                        <b-nav-form>
                            <b-form-input id="search" size="sm" class="mr-sm-2" placeholder="검색"></b-form-input>
                        </b-nav-form>
                    </b-navbar-nav>

                    <b-navbar-nav>
                        <b-nav-item-dropdown v-if="isAuthenticated" no-caret right>
                            <template v-slot:button-content>
                                <b-icon icon="person-circle"></b-icon>
                            </template>
                            <b-dropdown-text tag="span" class="username">{{getUsername()}}님</b-dropdown-text>
                            <b-dropdown-divider></b-dropdown-divider>
                            <b-dropdown-item to="/me">내정보</b-dropdown-item>
                            <b-dropdown-item @click.prevent="onClickLogout">로그아웃</b-dropdown-item>
                        </b-nav-item-dropdown>

                        <p  class="h4 mb-0 login">
                            <b-link v-if="!isAuthenticated" to="/login">로그인</b-link>
                        </p>
                    </b-navbar-nav>
                </b-collapse>
            </div>
        </b-navbar>
    </div>
</template>

<script>
    import store from '../vuex'

    export default {
        name: "Navbar",
        computed: {
            isAuthenticated() {
                return this.$store.state.login.isAuthenticated
            }
        },
        data() {
            return {
                // username: this.$store.state.login.userInfo === null ? '' : this.$store.state.login.userInfo.username
                username: '',
            }
        },
        methods: {
            onClickLogout() {
                store.dispatch('logout')
                    .then(() => this.$router.push('/'))
                    .catch(() => {})
            },
            getUsername(){
                return this.$store.getters.getUsername
            }
        }
    }
</script>

<style>
    .company{
        font-family: "GothicA1";
    }
    .nav-list span{
        color: #474747;
        font-weight: bold;
    }
    .navbar-brand{
        font-weight: bold;
        font-size: 1.5rem!important;
    }
    .nav-item:hover .dropdown-menu {
        display: block;
        margin-top: 0;
    }
    .nav-item:hover .nav-link{
        color: #fe696a!important;
        transition-duration: 0.3s;
    }
    .dropdown-item:hover {
        color: #fe696a!important;
        background: none!important;
    }
    .dropdown-menu {
        right: auto;
    }
    .form-inline {
        justify-content: center;
    }
    .categoryItem {
        margin: auto;
    }
    .categoryItem>li{
        margin: 0 0.75rem;
    }
    #search {
        text-align: center;
    }
    .login {
        min-width: 2.5rem;
        justify-content: center;
    }
    .login a{
        font-size: 0.80rem;
        color: black;
    }
    @media (min-width: 992px) {
        .login a{
            font-size: 1rem;
        }
    }
    .login:hover {
        text-decoration: none;
        color: black;
    }
    .username{

    }
</style>