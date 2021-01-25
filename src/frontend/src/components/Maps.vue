<template>
    <div class="mb-5">
        <naver-maps
                class="mx-auto"
                :width="width"
                :height="height"
                :map-options="mapOptions"
                :init-layers="initLayers"
                @load="onLoad">
            <naver-info-window
                    class="info-window"
                    :isOpen="info"
                    :marker="marker">
                <div class="info-window-container">
                    <h4>{{shopInfo}}</h4>
                </div>
            </naver-info-window>
            <naver-marker :lat="33.497950" :lng="126.537930" @click="onMarkerClicked" @load="onMarkerLoaded"/>
        </naver-maps>
    </div>

</template>

<script>
    export default {
        name: "Location",
        props: ['mapWidth', 'mapHeight'],
        data() {
            return {
                windowWidth: 0,
                width: this.mapWidth,
                height: this.mapHeight,
                info: false,
                map: null,
                marker: null,
                mapOptions: {
                    zoom: 17,
                    lat: 33.497826,
                    lng: 126.538032
                },
                initLayers: ['BACKGROUND', 'BACKGROUND_DETAIL', 'POI_KOREAN', 'TRANSIT', 'ENGLISH', 'CHINESE', 'JAPANESE'],
                icon: require('@/assets/map-marker.png'),
            }
        },
        computed: {
            shopInfo() {
                return '제주 중고쇼핑몰'
            }
        },
        mounted() {
            window.addEventListener('resize', () => {
                this.windowWidth = window.innerWidth
                if (this.windowWidth >= 1200) {
                    this.map.setSize({width: 450, height: 450})
                } else if(992 <= this.windowWidth && this.windowWidth < 1200) {
                    this.map.setSize({width: 400, height: 400})
                } else if(768 <= this.windowWidth && this.windowWidth < 992){
                    this.map.setSize({width: 300, height: 300})
                } else{
                    this.map.setSize({width: 345, height: 345})
                }
            })
        },
        methods: {
            onLoad(vue){
                this.map = vue
            },
            onMarkerClicked() {
                this.info = !this.info
            },
            onMarkerLoaded(vue) {
                vue.marker.setIcon(this.icon)
                this.marker = vue.marker
            }
        }

    }
</script>

<style scoped>

</style>