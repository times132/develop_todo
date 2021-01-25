<template>
    <div>
        <div>profile</div>
        <div>
            <label>User info:</label>
            <pre>{{ $store.state.login.userInfo }}</pre>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                user: null,
            }
        },
        created() {
            this.$store.dispatch("getUserInfo")
            .then()
        }
    }
</script>

<style scoped>

</style>