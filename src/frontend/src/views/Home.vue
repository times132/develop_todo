<template>
    <b-container>
        <b-row>
            <Carousel/>
        </b-row>

        <b-row class="mt-3 mb-3" align-v="center">
            <b-col cols="12" sm="6" class="simple-category">
                <b-row class="a">
                    <div>
                        <b-img src="http://times133.cdn3.cafe24.com/goods/aircon.jpg"/>
                        <span>에어컨</span>
                    </div>
                    <div>
                        <b-img src="http://times133.cdn3.cafe24.com/goods/appliances.jpg"/>
                        <span>생활가전</span>
                    </div>
                </b-row>

            </b-col>
            <b-col cols="12" sm="6" class="simple-category">
                <b-row class="a">
                    <div>
                        <b-img src="http://times133.cdn3.cafe24.com/goods/commercial-appliances.jpg"/>
                        <span>업소용</span>
                    </div>
                    <div>
                        <b-img src="http://times133.cdn3.cafe24.com/goods/office.jpg"/>
                        <span>사무용</span>
                    </div>
                </b-row>
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="12" md="7">
                <b-img fluid src="http://times133.cdn3.cafe24.com/home/bottom.jpg"/>
            </b-col>

            <b-col cols="12" md="5" class="map-div ml-auto">
                <Map :map-width='450' :map-height='450'/>
            </b-col>
        </b-row>

    </b-container>
</template>

<script>
    import Carousel from '../components/Carousel'
    import Map from '../components/Maps'

    export default {
        components: {
            Carousel,
            Map
        }
    }
</script>

<style scoped>
    h3 {
        margin: 40px 0 0;
    }
    ul {
        list-style-type: none;
        padding: 0;
    }
    li {
        display: inline-block;
        margin: 0 10px;
    }
    a {
        color: #42b983;
    }
    .a{
        display: flex;
        padding: 0.75rem 0;
    }
    .simple-category{
        /*background-color: black;*/
    }
    .simple-category div{
        margin: 0 auto;
    }
    .simple-category img{
        width: 7.5rem;
        height: 7.5rem;
        display: block;
        border-radius: 50%;
        margin-bottom: 0.5rem;
    }
    .simple-category span{
        font-weight: bold;
    }
    .map-div{
        overflow: hidden;
    }
</style>
