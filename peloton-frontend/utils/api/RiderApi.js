import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";
import { logApi } from "../Analytics";

export const RiderApi = {
  get: async (token, riderId) => {
    logApi("Rider", "Get");
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/riders/${riderId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
  post: async (token, race_id) => {
    logApi("Rider", "Post");
    try {
      const response = await Axios({
        method: "POST",
        baseURL: SERVER_BASE_URL,
        url: "/api/riders",
        data: {
          race_id,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.headers.location;
    } catch (error) {
      console.log(error.response.data.code);
      throw error;
    }
  },
  getInRace: async (token, raceId) => {
    logApi("Rider", "GetByRaceId");
    try {
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: `/api/riders/races/${raceId}`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      console.log(error);
      throw error;
    }
  },
};
