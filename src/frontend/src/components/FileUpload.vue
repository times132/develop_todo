<template>
    <div class="file-form">
        <label class="upload-div mb-0" for="input-img-icon">
            <img class="upload-img" :src="photo"/>
        </label>

        <div class="file-body">
            <span class="file-name" style="color: #938e8e">{{ fileName }}</span>
            <span v-if="isFile" class="reset-btn" @click="clearFile">X</span>
        </div>

        <input ref="fileInput" id="input-img-icon" class="input-image" type="file" name="uploadFile" v-on:input="selectImage"/>
    </div>
</template>

<script>
    export default {
        name: "FileUpload",
        props: ['initialFile'],
        data() {
            return {
                isFile: false,
                fileName: '대표 사진(10MB 이하)',
                photo: require('@/assets/photo.jpg')
            }
        },
        created() {
            if (this.initialFile != null) {
                this.fileName = this.initialFile.fileName
                this.isFile = true
            }
        },
        methods: {
            selectImage(event) {
                this.fileName = event.target.files[0].name
                this.$emit('event-data', event.target.files[0])
                this.isFile = true
            },
            clearFile() {
                this.$refs.fileInput.value = null;
                this.fileName = '대표 사진(10MB 이하)'
                this.isFile = false
                this.$emit('event-data', -1)
            }
        }
    }
</script>

<style>
    .file-form{
        border: 1px solid #ced4da;
        border-radius: .25rem;
        padding: .25rem;
        height: 38px;
    }
    .file-body{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        display: flex;
    }
    .file-name{
        width: 90%;
    }
    .reset-btn{
        width: 10%;
        position: relative;
        float: right;
        background-color: white;
        border: 1px solid black;
        font-weight: bold;
        margin: auto;
        cursor: pointer;
    }
    .upload-div{
        float: left;
    }
    .upload-img{
        margin-right: 0.25rem;
        width: 1.6rem;
        height: 1.6rem;
        cursor: pointer;
    }
    .input-image{
        visibility: hidden;
        height: 0;
        float: left;
        position: relative;
        top: -24px;
    }
</style>