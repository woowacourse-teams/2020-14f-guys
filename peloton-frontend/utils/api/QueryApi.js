import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const QueryApi = {
  getRaces: async (token) => {
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: "/api/queries/races",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  getMissions: async (token) => {
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: "/api/queries/missions/upcoming",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  getRaceCertifications: async (token, raceId) => {
    try {
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: `/api/queries/races/${raceId}/certifications`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
  getRaceDetail: async (token, raceId) => {
    try {
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: `/api/queries/races/${raceId}/detail`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
