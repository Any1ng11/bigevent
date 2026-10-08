<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})

import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();

const ResetPassword = ref({
    password: '',
    newPassword: '',
    rePassword: ''
})

//校验密码函数
const checkRePassword = (rule,value,callback) => {
    if(value ===''){
        callback(new Error('请再次输入密码'))
    }else if(value !== ResetPassword.value.newPassword){
        callback(new Error('请确保两次输入的密码一致'))
    }else{
        callback()
    }
}

const rules = {
    password: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '原密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '新密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    rePassword: [
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
const updatePassword = async()=>{
    let result = await userResetPasswordService(ResetPassword.value,tokenStore.token);

    ElMessage.success(result.msg? result.msg : '修改成功');

    //修改pinia中的数据
    userInfoStore.info.password = ResetPassword.value.newPassword
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
                <el-form :model="ResetPassword" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="password">
                        <el-input
                            v-model="ResetPassword.password"
                            style="width: 240px"
                            type="password"
                            placeholder="请输入原密码"
                            show-password
                        />
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input
                            v-model="ResetPassword.newPassword"
                            style="width: 240px"
                            type="password"
                            placeholder="请输入新密码"
                            show-password
                        />  
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePassword">
                        <el-input
                            v-model="ResetPassword.rePassword"
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
