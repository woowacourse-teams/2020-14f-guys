import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const RaceApi = {
  get: async (raceId, token) => {
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
};
