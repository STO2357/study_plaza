import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Logout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        localStorage.removeItem("accessToken"); // 토큰 삭제
        navigate("/"); // 메인으로 이동
    }, [navigate]);

    return null; // 화면에 아무것도 안 보여줘도 됨
};

export default Logout;