import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const MemberApi = {
  get: async (token) => {
    try {
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: "/api/members",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    }
    catch (error) {
      console.log(error);
    }
  },
  postProfile: async (token, formData) => {
    try {
      const response = await Axios({
        method: "POST",
        baseURL: SERVER_BASE_URL,
        url: "/api/members/profile",
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
        data: {
          formData,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  patchCash: async (token, cash) => {
    try {
      await Axios({
        method: "PATCH",
        baseURL: SERVER_BASE_URL,
        url: "/api/members/cash",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        data: {
          cash,
        },
      });
    }
    catch (error) {
      console.log(error);
    }
  },
};
