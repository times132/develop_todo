<template>
    <div class="pagination">
        <ul class="pagination">
            <li v-if="pagination.prev" class="page-item">
                <span @click="clickPrev">이전</span>
            </li>

            <li v-for="index in calRange" :key="index" class="page-item" :class="{'active': checkPage(index)}">
                <span @click="clickPage(index)">{{index}}</span>
            </li>

            <li v-if="pagination.next" class="page-item">
                <span @click="clickNext">다음</span>
            </li>
        </ul>
    </div>

</template>

<script>
    export default {
        name: "Pagination",
        props: ['pagingData', 'cri'],
        data() {
          return {
              pagination: this.pagingData,
              criteria: this.cri
          }
        },
        created() {

        },
        computed: {
            calRange() {
                let pageRange = []
                for (var i=this.pagingData.startPage; i <= this.pagingData.endPage; i++){
                    pageRange.push(i)
                }

                return pageRange
            }
        },
        watch: {
            pagingData: function (value) {
                this.pagination = value
            }
        },
        methods: {
            clickPage(page) {
                this.criteria.page = page
                window.scrollTo(0,0)
            },
            clickPrev() {
                this.criteria.page = this.pagingData.startPage - 1
                window.scrollTo(0,0)
            },
            clickNext() {
                this.criteria.page = this.pagingData.endPage + 1
                window.scrollTo(0,0)
            },
            checkPage(index) {
                return (this.criteria.page === index)
            }
        }
    }
</script>

<style>

</style>