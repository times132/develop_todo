<template>
    <b-container class="mt-3">
        <b-form @submit="onSubmit">
            <b-form-group
                    id="input-group-1"
                    label="Email address:"
                    label-for="input-1"
            >
                <b-form-input
                        id="input-1"
                        v-model="form.email"
                        type="email"
                        required
                        placeholder="이메일"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-2" label="Your Password:" label-for="input-2">
                <b-form-input
                        id="input-2"
                        v-model="form.password"
                        type="password"
                        required
                        placeholder="비밀번호"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="Your Username:" label-for="input-2">
                <b-form-input
                        id="input-3"
                        v-model="form.username"
                        required
                        placeholder="아이디"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-4" label="Your Name:" label-for="input-2">
                <b-form-input
                        id="input-4"
                        v-model="form.name"
                        required
                        placeholder="이름"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-4" label="Your Nickname:" label-for="input-2">
                <b-form-input
                        id="input-4"
                        v-model="form.nickname"
                        required
                        placeholder="닉네임"
                ></b-form-input>
            </b-form-group>

            <b-button type="submit" variant="primary">Submit</b-button>
        </b-form>

    </b-container>
</template>

<script>
    import { userSignup } from '../api'

    export default {
        name: "Signup",
        data() {
            return {
                form: {
                    email: '',
                    password: '',
                    username: '',
                    name: '',
                    nickname: ''
                }
            }
        },
        methods: {
            onSubmit(event) {
                event.preventDefault()
                userSignup(JSON.stringify(this.form))
                    .then(() => {
                        alert("등록되었습니다.")
                        this.$router.replace(this.$route.query.redirect || '/login')
                    })
                    .catch(() => alert("실패하였습니다."))
            }
        }
    }
</script>

<style scoped>

</style>