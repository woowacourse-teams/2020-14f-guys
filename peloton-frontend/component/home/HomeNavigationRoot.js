import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import Home from "./homemain/Home";
import RaceDetail from "./race/RaceDetail";
import CategorySelection from "./racecreate/CategorySelection";
import RaceShareButton from "./race/RaceShareButton";
import InputRaceInfo from "./racecreate/InputRaceInfo";
import InputRaceDates from "./racecreate/InputRaceDates";
import InputRaceFee from "./racecreate/InputRaceFee";
import RaceDeepLinkPage from "./race/RaceDeepLinkPage";
import GoBackButton from "./race/GoBackButton";
import InputRaceMissionTime from "./racecreate/InputRaceMissionTime";
import InputRaceMissionDays from "./racecreate/InputRaceMissionDays";
import CertificationDetail from "../certification/CertificationDetail";
import ImageDetail from "../certification/ImageDetail";

const HomeStack = createStackNavigator();

const HomeNavigationRoot = () => {
  return (
    <HomeStack.Navigator initialRouteName="Home">
      <HomeStack.Screen
        name="Home"
        component={Home}
        options={{
          title: "Peloton",
        }}
      />
      <HomeStack.Screen
        name="RaceDetail"
        component={RaceDetail}
        options={{
          title: "진행중인 레이스",
          headerTransparent: true,
          headerTitle: false,
          headerLeft: () => <GoBackButton />,
          headerRight: () => <RaceShareButton />,
        }}
      />
      <HomeStack.Screen
        name="CategorySelection"
        component={CategorySelection}
        options={{ title: "레이스 카테고리 선택" }}
      />
      <HomeStack.Screen
        name="InputRaceInfo"
        component={InputRaceInfo}
        options={{ title: "레이스 정보 입력" }}
      />
      <HomeStack.Screen
        name="InputRaceDates"
        component={InputRaceDates}
        options={{ title: "레이스 기간 선택" }}
      />
      <HomeStack.Screen
        name="InputRaceMissionTime"
        component={InputRaceMissionTime}
        options={{ title: "레이스 미션 시간" }}
      />
      <HomeStack.Screen
        name="InputRaceMissionDays"
        component={InputRaceMissionDays}
        options={{ title: "레이스 미션 주기" }}
      />
      <HomeStack.Screen
        name="InputRaceFee"
        component={InputRaceFee}
        options={{ title: "레이스 입장료 입력" }}
      />
      <HomeStack.Screen
        name="RaceDeepLinkPage"
        component={RaceDeepLinkPage}
        options={{ title: "레이스 참여" }}
      />
      <HomeStack.Screen
        name="CertificationDetail"
        component={CertificationDetail}
        options={{
          title: "진행중인 레이스",
          headerTransparent: true,
          headerTitle: false,
          headerLeft: () => <GoBackButton />,
        }}
      />
      <HomeStack.Screen
        name="ImageDetail"
        component={ImageDetail}
        options={{ headerShown: true, title: "상세보기" }}
      />
    </HomeStack.Navigator>
  );
};

export default HomeNavigationRoot;
