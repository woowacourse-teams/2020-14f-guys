import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";
import { logApi } from "../Analytics";

export const RaceApi = {
  get: async (token, raceId) => {
    logApi("Race", "Get");
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/races/${raceId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  post: async (token, data) => {
    logApi("Race", "Post");
    try {
      const { headers } = await Axios({
        method: "POST",
        baseURL: SERVER_BASE_URL,
        url: "/api/races",
        data,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return headers;
    } catch (error) {
      console.log(error.response.data.code);
      throw error;
    }
  },
};
