import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const MissionApi = {
  get: async (token, missionId) => {
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/missions/${missionId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
};
