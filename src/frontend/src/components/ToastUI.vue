<template>
    <Editor
            ref="tuiEditor"
            height="500px"
            initialEditType="wysiwyg"
            :initialValue="form.content"
    />
</template>

<script>
    import '../css/codemirror.css'
    import '@toast-ui/editor/dist/toastui-editor.css'

    import { Editor } from '@toast-ui/vue-editor'

    export default {
        name: "ToastUI",
        props: ['initialValue'],
        data: function() {
            return {
                form: {
                    writer: '',
                    content: this.initialValue === null ? '' : this.initialValue
                }
            }
        },
        components: {
            Editor
        },
        methods: {
            getHtml() {
                return this.$refs.tuiEditor.invoke('getHtml')
            }
        }
    }
</script>