<template>
    <b-container>

        <b-row align-h="between" class="mt-3">
            <b-col sm="2" class="total-goods">
                <span >등록 제품 : {{pagination.total}}개</span>
            </b-col>
            <b-col sm="9">
                <b-nav>
                    <b-col md="3" sm="4" cols="4" class="category-list" v-for="(category, i) in category[categoryIndex]" :key="i">
                        <b-nav-item :to="'/goods/'+category.itemNum">
                            {{category.name}}
                        </b-nav-item>
                    </b-col>
                </b-nav>
            </b-col>
        </b-row>

        <div v-if="isDataFetch" class="mt-4">
            <b-row v-for="i in 4" :key="i">
                <b-col class="goods-list" cols="6" sm="3" v-for="(goods, j) in goods.slice((i-1)*4, i*4)" :key="j">
                    <b-card no-body>
                        <b-img class="goods-img" fluid :src="'/api/display?imageName='+thumbnail(goods.image)" @error="$event.target.src=noImage" @click="clickRow(goods.gid)"/>
                        <b-card-body class="goods-body">
                            <span>{{goods.title}}</span>
                        </b-card-body>
                    </b-card>
                </b-col>
            </b-row>
        </div>

        <!-- 페이징 -->
        <Pagination :paging-data="pagination" :cri="criteria"/>

        <router-link to="/goods/write">
            <b-button v-if="checkAdmin" squared variant="outline-secondary">글쓰기</b-button>
        </router-link>

    </b-container>
</template>

<script>
    import { getGoodsList } from "../../api";
    import Pagination from "../../components/Pagination";

    export default {
        name: "List",
        components: {
            Pagination
        },
        computed: {
            checkAdmin() {
                if (!this.$store.getters.getAuth.includes('ROLE_ADMIN'))
                    return false
                return true
            }
        },
        props: {
            categoryNum: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                goods: null,
                criteria: {
                    page: this.$route.params.page===undefined ? 1 : this.$route.params.page,
                    pageSize: 16,
                    pageRange: 5,
                    type: '',
                    keyword: ''
                },
                pagination: {},
                isDataFetch: false,
                category: [
                    [{itemNum: '011', name: '스텐드'}, {itemNum: '012', name: '중대형'}, {itemNum: '013', name: '벽걸이'}],
                    [{itemNum: '021', name: '냉장고'}, {itemNum: '022', name: '세탁기'}, {itemNum: '023', name: 'TV'}],
                    [{itemNum: '031', name: '선반'}, {itemNum: '032', name: '냉장/냉동고'}, {itemNum: '033', name: '화구'}],
                    [{itemNum: '041', name: '책상'}, {itemNum: '042', name: '사무2'}, {itemNum: '043', name: '사무3'}]
                ],
                categoryIndex: 0,
                noImage: require('@/assets/no-image.jpg')
            }
        },
        created() {
            this.getList()
            this.categoryIndex = parseInt(this.categoryNum.slice(0,2))-1
        },
        watch: {
            criteria: {
                deep: true,
                handler() {
                    this.getList()
                }
            }
        },
        methods: {
            clickRow(gid) {
                this.$router.push(`/goods/${this.categoryNum}/${gid}`)
                this.$store.dispatch('setCriteria', this.criteria)
            },
            getList(){
                getGoodsList(this.$route.params.categoryNum, this.criteria)
                    .then(({data}) => {
                        this.pagination = data.pagination
                        this.goods = data.goodsList
                        this.isDataFetch = true
                    })
            },
            thumbnail(value) {
                if (value === null) {
                    return null
                }else {
                    return value.path+'/s_'+value.uuid+'_'+value.fileName
                }
            }
        },
    }
</script>

<style>
    .total-goods{
        margin: auto 0;
    }
    .total-goods span{
        font-size: 0.85rem;
        color: #7b7b7b;
    }
    .category-list{
        border: 1px solid #a7a7a7;
    }
    .goods-list{
        margin-bottom: 1.5rem;
    }
    .goods-img{
        border-bottom: 1px solid rgba(0,0,0,.125);
    }
    .goods-body{
        padding-bottom: 1rem;
        text-align: left;
    }
    .goods-body span{
        font-family: "InfinitySans";
        font-size: 1.2rem;
        font-weight: bold;
    }
    .pagination{
        justify-content: center;
    }
    .pagination span{
        margin: 0 0.2rem;
        font-size: 1.25rem;
    }
    .pagination span:hover:not(.active){
        cursor: pointer;
        background-color: #ddd;
    }
    .active{
        font-weight: bold;
        text-decoration: underline;
    }
    .nav-link{
        color: black;
    }

</style>