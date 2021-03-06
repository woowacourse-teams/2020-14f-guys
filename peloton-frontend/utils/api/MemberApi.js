import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";
import { logApi } from "../Analytics";

export const MemberApi = {
  get: async (token) => {
    logApi("Member", "Get");
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: "/api/members",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  getById: async (token, memberId) => {
    logApi("Member", "GetById");
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/members/${memberId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  postProfile: async (token, formData) => {
    logApi("Member", "UpdateProfile");
    try {
      const response = await Axios.post(
        `${SERVER_BASE_URL}/api/members/profile`,
        formData,
        {
          headers: {
            ContentType: "multipart/form-data",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data.image_url;
    } catch (error) {
      console.log(error.response.data.code);
    }
  },
  patchCash: async (token, cash) => {
    logApi("Member", "UpdateCash");
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
    } catch (error) {
      console.log(error.response.data.code);
    }
  },
  patchName: async (token, name) => {
    logApi("Member", "UpdateName");
    try {
      await Axios({
        method: "PATCH",
        baseURL: SERVER_BASE_URL,
        url: "/api/members/name",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        data: {
          name,
        },
      });
    } catch (error) {
      console.log(error.response.data.code);
    }
  },
  delete: async (token) => {
    logApi("Member", "Delete");
    await Axios({
      method: "DELETE",
      baseURL: SERVER_BASE_URL,
      url: "/api/members",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  },
};
