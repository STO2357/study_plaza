import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import InputField from "../../components/common/inputfield/InputField";
import Button from "../../components/common/button/Button";
import { loginApi } from "../../api/auth";

import styles from "./authStyle/Login.module.css";

const Login = () => {
    const navigate = useNavigate();
  const [userId, setMemberId] = useState("");
  const [password, setPassword] = useState("");

   const handleLogin = async () => {
    try {
      const res = await loginApi({ userId, password });
        // 백엔드 응답이 ApiResponse.success 형태라면 아래처럼 접근
        const token = res.data?.data;
        // 토큰을 localStorage에 저장 → axiosInstance가 자동으로 Authorization 헤더 붙임
        localStorage.setItem("accessToken", token);
      console.log("로그인 성공, 토큰:", token);
        navigate("/");
    } catch (err) {
      console.error(err.response?.data?.message || err.message);
    }
  };

  return (
    <div className={styles.container}>
      <h1>로그인</h1>
      <InputField
        label="아이디"
        type="text"
        value={userId}
        onChange={(e) => setMemberId(e.target.value)}
        placeholder="아이디를 입력하세요"
      />
      <InputField
        label="비밀번호"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="비밀번호를 입력하세요"
      />
      <Button variant="primary" size="medium" onClick={handleLogin}>
        로그인
      </Button>
    </div>
  );
};

export default Login;
