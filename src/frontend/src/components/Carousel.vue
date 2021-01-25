<template>
    <div id="carousel">
        <b-carousel
                style="text-shadow: 0px 0px 2px #000"
                :interval=4000
                indicators
                controls
                img-width="1140"
                img-height="450"
        >
            <b-carousel-slide
                    img-src="http://times133.cdn3.cafe24.com/home/1.jpg"
            ></b-carousel-slide>
            <b-carousel-slide
                    img-src="http://times133.cdn3.cafe24.com/home/2.jpg"
            ></b-carousel-slide>
        </b-carousel>
    </div>
</template>

<script>
    export default {
        name: "Carousel",
        data() {
            return {

            }
        }
    }
</script>

<style scoped>
    @import "../css/indicator.css";
    .carousel-item{
        max-height: 480px;
    }
    .carousel-item img{
        width: 100%;
        height: 20vh;
    }
</style>