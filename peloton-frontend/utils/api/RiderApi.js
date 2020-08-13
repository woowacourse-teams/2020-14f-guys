import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const RiderApi = {
  post: async (token, race_id) => {
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
      console.log(error);
      throw error;
    }
  },
  getInRace: async (token, raceId) => {
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
