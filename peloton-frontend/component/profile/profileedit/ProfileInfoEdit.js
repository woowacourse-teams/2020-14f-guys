import React, { useState } from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { memberInfoState, memberTokenState } from "../../../state/member/MemberState";
import { MemberApi } from "../../../utils/api/MemberApi";
import { loadingState } from "../../../state/loading/LoadingState";
import { AntDesign } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const ProfileEditInfo = () => {
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();

  const [name, setName] = useState(memberInfo.name);

  const requestChangeName = async () => {
    setIsLoading(true);
    try {
      await MemberApi.patchName(token, name);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
    } catch (error) {
      console.log(error);
    }
    setIsLoading(false);
  };

  return (
    <View style={styles.infoContainer}>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>🚴 ‍️Rider Name</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.name}</Text>
        </View>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate("NameUpdate")}>
          <Text style={styles.buttonText}>수정하기</Text>
          <AntDesign name="right" size={18} color={COLOR.GREEN2} />
        </TouchableOpacity>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>😀 Kakao ID</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.kakao_id}</Text>
        </View>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>💰 Cash</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.cash}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("CashUpdate")}
        >
          <Text style={styles.buttonText}>충전하기</Text>
          <AntDesign name="right" size={18} color={COLOR.GREEN2} />
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
    borderColor: "#F0F3F4",
    borderRadius: 20,
    shadowColor: "rgba(80,92,98,0.04)",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowRadius: 10,
    shadowOpacity: 1,
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
    fontSize: 17,
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
