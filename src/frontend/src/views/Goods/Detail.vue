<template>
    <b-container>
        <div class="button-group mt-3">
            <b-button v-if="compareUser" @click="modifyGoods" squared size="sm">수정</b-button>
            <b-button v-if="compareUser" @click="showModal" squared variant="outline-danger" size="sm">삭제</b-button>
            <b-button @click="moveList" squared variant="primary" size="sm">목록</b-button>

            <b-modal ref="delete-modal" hide-footer>
                <div>
                    <h4>삭제하시겠습니까?</h4>
                </div>

                <div class="button-group">
                    <button @click="deleteGoods" class="modal-button confirm">확인</button>
                    <button @click="hideModal" class="modal-button cancel">취소</button>
                </div>
            </b-modal>
        </div>

        <div class="mt-1">
            <b-row no-gutters>
                <!-- 대표 이미지 -->
                <b-col md="5" class="goods-img-container">
                    <div v-if="isDataFetch" class="goods-detail">
                        <b-card-img class="goods-detail-img" :src="'/api/display?imageName=' + imageUrl" width="320" @error="$event.target.src=noImage"/>
                    </div>
                </b-col>
                <b-col md="7">
                        <h2 class="title">{{goods.title}}</h2>
                        <h3 class="price">{{formatPrice(goods.price)}} 원 ~</h3>
                        <div class="simple-banner">
                            <b-img fluid src="http://times133.cdn3.cafe24.com/goods/small-banner.jpg"/>
                        </div>
                </b-col>
            </b-row>
            <!-- 본문 -->
            <b-row class="my-2">
                <b-col class="content">
                    <viewer v-if="goods.content != null" :initialValue="goods.content"/>
                </b-col>
            </b-row>
        </div>
    </b-container>
</template>

<script>
    import { deleteGoods, getDetail } from "../../api";

    export default {
        name: "Detail",
        computed: {
            getCri() {
                return this.$store.getters.getCriteria
            },
            compareUser(){
                if (this.$store.getters.getIsAuth){
                    if (this.$store.state.login.userInfo.username === this.goods.writer) {
                        return true
                    }
                }
                return false
            }
        },
        data() {
            return {
                goods: {
                    gid: this.$route.params.gid,
                    categoryItem: null,
                    title: '',
                    price: 0,
                    content: null,
                    writer: '',
                    image: null
                },
                imageUrl: '',
                imageData: '',
                width: 320,
                height: 480,
                isDataFetch: false,
                noImage: require('@/assets/no-image.jpg')
            }
        },
        created() {
            getDetail('/api/goods/detail', this.goods.gid)
                .then(({data}) => {
                    this.goods.content = data.content
                    this.goods.title = data.title
                    this.goods.price = data.price
                    this.goods.writer = data.writer
                    this.goods.categoryItem = data.categoryItem

                    if (data.image !== null) {
                        this.goods.image = data.image
                        this.imageUrl = data.image.path + '/' + data.image.uuid + '_' + data.image.fileName
                    }
                    this.isDataFetch = true
                })
        },
        mounted() {

        },
        methods: {
            moveList() {
                this.$router.push({name: 'goods', params: {'categoryNum': this.$route.params.categoryNum, 'page': this.getCri.page, 'pageSize': this.getCri.pageSize, 'type': this.getCri.type, 'keyword': this.getCri.keyword}})
            },
            modifyGoods() {
                this.$router.push({name: 'modify', params: {goods: this.goods}})
            },
            deleteGoods() {
                deleteGoods(this.goods.gid)
                    .then(({data}) => {
                        console.log(data)
                        this.$router.push({name: 'goods', params: {'categoryNum': this.$route.params.categoryNum, 'page': this.getCri.page, 'pageSize': this.getCri.pageSize, 'type': this.getCri.type, 'keyword': this.getCri.keyword}})
                    })
            },
            showModal() {
                this.$refs['delete-modal'].show()
            },
            hideModal() {
                this.$refs['delete-modal'].hide()
            },
            formatPrice(value){
                let val = new Number(value)
                return val.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1,")
            }
        }
    }
</script>

<style scoped>
    .goods-img-container{
        height: 480px;
        overflow: hidden;
    }
    .goods-detail {
        height: 100%;
        display:flex;
        align-items: center;
    }
    .goods-detail-img{
        height: auto;
        width: 100%;
        align-self: center;
    }
    .button-group{
        text-align: right;
    }
    .button-group button{
        margin: 0 0.1rem;
    }
    .modal-button{
        font-size: 1rem;
    }
    .confirm{
        color: white;
        background-color: #408af6;
        border-radius: 0.25rem;
        border: 1px solid #408af6;
        padding: 0.2rem 0.7rem;
    }
    .confirm:hover{
        background-color: #3683f3;
    }
    .cancel{
        color: #408af6;
        background-color: white;
        border-radius: 0.25rem;
        border: 1px solid #c4c4c4;
        padding: 0.2rem 0.7rem;
    }
    .cancel:hover{
        background-color: #efefef;
    }
    .title{
        margin: 0 2rem;
        padding: 0.5rem 0;
        text-align: left;
        font-family: "GothicA1";
        border-bottom: 1px solid black;
    }
    .price{
        padding: 1rem 3rem;
        text-align: left;
        font-weight: bold;
        font-family: "InfinitySans";
        color: #575757;
    }
    .simple-banner{
        padding-top: 1rem;
        right: 0;
        bottom: 0;
        position: absolute;
    }
    @media (max-width: 768px) {
        .simple-banner{
            position: relative;
            text-align: center;
        }
    }
    .content{
        margin-top: 1rem;
    }
</style>