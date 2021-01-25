<template>
    <b-container fluid id="footer">
        <b-row class="text-center">
            <b-col cols="3" class="company"><h4>중고쇼핑몰</h4></b-col>
            <b-col>
                <b-row class="justify-content-md-center">
                    <ul class="a">
                        <li>상호 : {{ company }} | </li>
                        <li>대표 : {{ ceo }} | </li>
                        <li>대표 번호 : {{ phoneNum }} | </li>
                        <li>주소 : {{ address1 }}</li><br>
                        <li>사업자등록번호 : {{ license }} | </li>
                        <li>개인정보책임자 : {{ personalDataManager }}</li><br>
                        <li>Copyright ⓒ 착한 중고. All Rights Reserved | Design times132</li>
                    </ul>
                </b-row>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "Footer",
        data() {
            return {
                company: '중고쇼핑몰',
                ceo: '박정현',
                personalDataManager: '박병진',
                license: '211-02-30388',
                phoneNum: '064-711-4694',
                address1: '제주 제주시 가령로 75 중고쇼핑몰',
                address2: '제주 제주시 연북로 733 착한중고',
                email: 'times132@naver.com'
            }
        }
    }
</script>

<style scoped>
    .company{
        margin: auto 0;
        color: black;
        font-weight: bold;
    }
    ul {
        margin: 0.25rem;
    }
    .a li{
        display: inline-block;
        font-size: 12px;
        padding: 0 2px 0 2px;
    }
</style>