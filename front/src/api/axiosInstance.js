import axios from "axios";

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || "http://localhost:8080",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token && !config.url.includes("/login") && !config.url.includes("/signup")) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const { status } = error.response;
      if (status === 401) {
        console.warn("로그인 세션이 만료되었습니다.");
        localStorage.removeItem("accessToken");
        window.location.href = "/login";
      }
      if (status === 403) {
        console.error("접근 권한이 없습니다.");
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;