<template>
    <div id="app">
        <Nav id="header"/>

        <router-view id="main" :key="$route.fullPath"/>

        <Footer id="footer"/>
    </div>
</template>

<script>
    import Nav from './components/Navbar'
    import Footer from './components/Footer'

    export default {
        name: 'App',
        components: {
            Footer,
            Nav
        },
    }
</script>

<style>
    html {
        height: 100%;
    }
    body {
        margin: 0;
        height: 100%;
    }
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        height: 100%;
        display: flex;
        flex-direction: column;
    }
    #header{
        background-color: #dae2e3;
        margin-bottom: 1rem;
        position: fixed;
        left: 0;
        top: 0;
        width: 100vw;
        z-index: 100;
        transform: translate(0, -4px);
        padding-top: 0.25rem;
    }
    #main{
        padding-top: 4.5rem;
        flex: 1 0 auto;
    }
    #footer{
        flex-shrink: 0;
        padding: 20px;
        background-color: gray;
        color: white;
        margin-top: 2rem;
    }
    @font-face {
        font-family: "GothicA1";
        src: local("GothicA1"),
        url("./font/GothicA1.ttf") format("truetype");
    }
    @font-face {
        font-family: "InfinitySans";
        src: local("InfinitySans"),
        url("./font/InfinitySans.ttf") format("truetype");
    }
</style>
