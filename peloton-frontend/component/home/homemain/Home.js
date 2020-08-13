import React, { useEffect } from "react";
import { ScrollView, StyleSheet, View } from "react-native";

import RaceList from "./RaceList";
import HomeBanner from "./HomeBanner";
import { COLOR, TOKEN_STORAGE } from "../../../utils/constants";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { navigateWithoutHistory } from "../../../utils/util";
import AsyncStorage from "@react-native-community/async-storage";
import { useNavigation } from "@react-navigation/core";
import { MemberApi } from "../../../utils/api/MemberApi";
import { memberInfoState, memberTokenState } from "../../../state/member/MemberState";
import LoadingIndicator from "../../../utils/LoadingIndicator";

const Home = () => {
  const setUserInfo = useSetRecoilState(memberInfoState);
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const navigation = useNavigation();

  useEffect(() => {
    setIsLoading(true);
    const fetchUser = async () => {
      const storageToken = await AsyncStorage.getItem(TOKEN_STORAGE);
      if (!storageToken) {
        await AsyncStorage.setItem(TOKEN_STORAGE, token);
      }
      if (!token) {
        alert("비정상적인 접근입니다.");
        navigateWithoutHistory(navigation, "Login");
      }
      const userInfo = await MemberApi.get(token);
      setUserInfo(userInfo);
    };
    fetchUser();
    setIsLoading(false);
  }, []);

  return (
    <LoadingIndicator>
      <ScrollView>
        <View style={styles.raceTitle}>
          <HomeBanner/>
        </View>
        <View style={styles.raceList}>
          <RaceList/>
        </View>
      </ScrollView>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
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
