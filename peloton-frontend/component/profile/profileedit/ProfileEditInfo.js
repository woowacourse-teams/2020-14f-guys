import React, { useState } from "react";
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Axios from "axios";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { COLOR, SERVER_BASE_URL } from "../../../utils/constants";
import { memberInfoState, memberTokenState } from "../../../state/member/MemberState";

const ProfileEditInfo = () => {
  const userInfo = useRecoilValue(memberInfoState);
  const setUserInfo = useSetRecoilState(memberInfoState);
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();

  const [name, setName] = useState(userInfo.name);

  const requestChangeName = () => {
    Axios.patch(
      `${SERVER_BASE_URL}/api/members/name`,
      {
        name: name,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    )
      .then(async () => {
        const response = await Axios.get(`${SERVER_BASE_URL}/api/members`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        await setUserInfo(response.data);
        navigation.navigate("ProfileDetail");
      })
      .catch((error) => console.log(error));
  };

  return (
    <View style={styles.infoContainer}>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>🚴 ‍️Rider Name</Text>
          <TextInput
            style={styles.eachInfoValue}
            onChangeText={(text) => setName(text)}
            value={name}
          />
        </View>
        <TouchableOpacity style={styles.button} onPress={requestChangeName}>
          <Text style={styles.buttonText}>수정하기</Text>
          <Image
            style={styles.buttonImage}
            source={require("../../../assets/icn_back_dark.png")}
          />
        </TouchableOpacity>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>😀 Kakao ID</Text>
          <Text style={styles.eachInfoValue}>{userInfo.kakaoId}</Text>
        </View>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>💰 Cash</Text>
          <Text style={styles.eachInfoValue}>{userInfo.cash}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("CashUpdate")}
        >
          <Text style={styles.buttonText}>충전하기</Text>
          <Image
            style={styles.buttonImage}
            source={require("../../../assets/icn_back_dark.png")}
          />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  infoContainer: {
    flex: 2,
    height: 300,
    alignItems: "center",
    marginHorizontal: 18,
    justifyContent: "space-around",
    backgroundColor: COLOR.WHITE,
    borderWidth: 0,
    borderColor: COLOR.WHITE3,
    borderRadius: 20,
    shadowColor: "rgba(80,92,98,0.04)",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowRadius: 10,
    shadowOpacity: 1,
  },
  editIcon: {
    color: COLOR.BLACK,
  },
  eachInfo: {
    flex: 1,
    width: "100%",
    flexDirection: "row",
    borderWidth: 1,
    borderColor: COLOR.WHITE3,
  },
  eachTextBox: {
    flex: 5,
    justifyContent: "center",
    paddingLeft: 25,
  },
  editButton: {
    flex: 1,
    justifyContent: "center",
    alignItems: "flex-end",
    paddingRight: 25,
  },
  button: {
    flex: 2,
    flexDirection: "row",
    justifyContent: "flex-end",
    alignItems: "center",
    paddingRight: 25,
  },
  buttonText: {
    color: COLOR.GREEN2,
    paddingRight: 5,
  },
  eachInfoKey: {
    fontSize: 17,
    fontWeight: "800",
    color: COLOR.GREEN3,
    lineHeight: 35,
    paddingBottom: 5,
  },
  eachInfoValue: {
    color: COLOR.GREEN2,
    marginLeft: 26,
    marginRight: 60,
    paddingBottom: 7,
    borderBottomColor: COLOR.WHITE4,
    borderBottomWidth: 1,
  },
  editButtonImage: {
    width: 20,
    height: 20,
  },
  buttonImage: {
    width: 10,
    height: 10,
  },
});

export default ProfileEditInfo;
