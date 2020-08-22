import Axios from "axios";

import { SERVER_BASE_URL } from "../constants";

export const CertificationApi = {
  post: async (token, formData) => {
    const { headers } = await Axios.post(
      `${SERVER_BASE_URL}/api/certifications`,
      formData,
      {
        headers: {
          ContentType: "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      },
    );
    return headers;
  },
  update: async (token, formData, id) => {
    const { headers } = await Axios.post(
      `${SERVER_BASE_URL}/api/certifications/update/${id}`,
      formData,
      {
        headers: {
          ContentType: "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      },
    );
    return headers;
  },
  get: async (token, certificationId) => {
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/certifications/${certificationId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
};
