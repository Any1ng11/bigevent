<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})

import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();

const ResetPassword = ref({
    old_Pwd: '',
    new_Pwd: '',
    re_Pwd: ''
})

//校验密码函数
const checkRePassword = (rule,value,callback) => {
    if(value ===''){
        callback(new Error('请再次输入密码'))
    }else if(value !== ResetPassword.value.new_Pwd){
        callback(new Error('请确保两次输入的密码一致'))
    }else{
        callback()
    }
}

const rules = {
    old_Pwd: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '原密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    new_Pwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '新密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    re_Pwd: [
        {
            required: true,
            validator:checkRePassword,
            trigger: 'blur'
        }
    ]
}

//重置密码
import {userResetPasswordService} from '@/api/user.js'
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'
const router = useRouter();
const formRef = ref()
const updatePassword = async()=>{
    try {
        await formRef.value.validate()
    } catch {
        return
    }

    let result = await userResetPasswordService(ResetPassword.value,tokenStore.token);

    ElMessage.success(result.msg? result.msg : '修改成功');

    //1.清空pinia中存储的token以及个人资料
    tokenStore.removeToken()
    userInfoStore.removeInfo()

    //2.跳转到登录页面
    router.push('/login')
    ElMessage({
        type: 'success',
        message: '请重新登录',
    })
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form ref="formRef" :model="ResetPassword" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="old_Pwd">
                        <el-input
                            v-model="ResetPassword.old_Pwd"
                            style="width: 240px"
                            type="password"
                            placeholder="请输入原密码"
                            show-password
                        />
                    </el-form-item>
                    <el-form-item label="新密码" prop="new_Pwd">
                        <el-input
                            v-model="ResetPassword.new_Pwd"
                            style="width: 240px"
                            type="password"
                            placeholder="请输入新密码"
                            show-password
                        />  
                    </el-form-item>
                    <el-form-item label="确认密码" prop="re_Pwd">
                        <el-input
                            v-model="ResetPassword.re_Pwd"
                            style="width: 240px"
                            type="password"
                            placeholder="请输入确认密码"
                            show-password
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<style scoped>
.input-group {
  display: flex;
  align-items: center;
  gap: 1em;
}
</style>
