import React, { useEffect } from "react";
import { ScrollView, StyleSheet, View } from "react-native";

import RaceList from "./RaceList";
import HomeBanner from "./HomeBanner";
import Axios from "axios";
import { SERVER_BASE_URL } from "../../../utils/constants";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import { userInfoState, userTokenState } from "../../atoms";
import { loadingState } from "../../../state/loading/LoadingState";

const Home = () => {
  const setUserInfo = useSetRecoilState(userInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(userTokenState);

  useEffect(() => {
    const fetchUser = async () => {
      setIsLoading(true);
      console.log(token);
      const response = await Axios({
        mehtod: "GET",
        baseURL: SERVER_BASE_URL,
        url: "/api/members",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUserInfo(response.data);
      console.log(response.data);
    };
    fetchUser();
    setIsLoading(false);
  }, []);

  return (
    <ScrollView>
      <View style={styles.raceTitle}>
        <HomeBanner />
      </View>
      <View style={styles.raceList}>
        <RaceList />
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  raceTitle: {
    flex: 1,
    minHeight: 280,
  },
  raceList: {
    flex: 1,
  },
});

export default Home;
