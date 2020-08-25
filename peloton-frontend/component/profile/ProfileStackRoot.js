import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ProfileDetail from "./profiledetail/ProfileDetail";
import ProfileEdit from "./profileedit/ProfileEdit";
import CashUpdate from "./profileedit/CashUpdate";
import NameUpdate from "./profileedit/NameUpdate";
import License from "../setting/license/License";

const ProfileStack = createStackNavigator();

const ProfileStackRoot = () => {
  return (
    <ProfileStack.Navigator initialRouteName="ProfileDetail">
      <ProfileStack.Screen
        name="ProfileDetail"
        component={ProfileDetail}
        options={{
          title: "프로필",
        }}
      />
      <ProfileStack.Screen
        name="ProfileEdit"
        component={ProfileEdit}
        options={{
          title: "내 정보 수정",
        }}
      />
      <ProfileStack.Screen
        name="CashUpdate"
        component={CashUpdate}
        options={{
          title: "충전하기",
        }}
      />
      <ProfileStack.Screen
        name="NameUpdate"
        component={NameUpdate}
        options={{
          title: "닉네임 변경",
        }}
      />
    </ProfileStack.Navigator>
  );
};

export default ProfileStackRoot;
