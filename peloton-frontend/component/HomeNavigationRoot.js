import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import Home from "./home/homemain/Home";
import RaceDetail from "./home/race/RaceDetail";
import CategorySelection from "./home/racecreate/CategorySelection";
import RaceShareButton from "./home/race/RaceShareButton";
import InputRaceInfo from "./home/racecreate/InputRaceInfo";
import InputRaceDates from "./home/racecreate/InputRaceDates";
import InputRaceFee from "./home/racecreate/InputRaceFee";
import RaceDeepLinkPage from "./home/race/RaceDeepLinkPage";
import GoBackButton from "./home/race/GoBackButton";
import InputRaceMissionTime from "./home/racecreate/InputRaceMissionTime";
import InputRaceMissionDays from "./home/racecreate/InputRaceMissionDays";

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
    </HomeStack.Navigator>
  );
};

export default HomeNavigationRoot;
