<template>
    <div>
        <Viewer v-if='content != null' :initialValue='content'/>
    </div>
</template>

<script>
    import '../css/codemirror.css'
    import '@toast-ui/editor/dist/toastui-editor.css'
    import { Viewer } from '@toast-ui/vue-editor'

    export default {
        name: "ToastViewer",
        components: {
            Viewer
        },
        props: ['toastViewData'],
        data() {
            return {
                content: this.toastViewData
            }
        }
    }
</script>

<style scoped>

</style>