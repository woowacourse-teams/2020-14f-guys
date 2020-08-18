import React, { useState } from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { useNavigation } from "@react-navigation/core";
import { MemberApi } from "../../../utils/api/MemberApi";
import { loadingState } from "../../../state/loading/LoadingState";

const NameUpdate = () => {
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [name, setName] = useState(memberInfo.name);
  const setIsLoading = useSetRecoilState(loadingState);

  const requestChangeName = async () => {
    setIsLoading(true);
    try {
      await MemberApi.patchName(token, name);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      navigation.navigate("ProfileEdit");
    } catch (error) {
      alert(error.response.data.code);
      console.log(error.response.data.code);
    }
    setIsLoading(false);
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <View style={styles.container}>
        <View style={styles.chargeContainer}>
          <View style={{ width: "100%" }}>
            <Text style={styles.chargeText}>변경할 닉네임을 입력해주세요</Text>
            <TextInput
              style={styles.chargeInput}
              onChangeText={(text) => setName(text)}
              value={name}
            />
          </View>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.button} onPress={requestChangeName}>
            <Text style={styles.buttonText}>변경하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE4,
  },
  chargeContainer: {
    flex: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  chargeText: {
    marginLeft: "14%",
    color: COLOR.GREEN3,
    fontWeight: "bold",
    fontSize: 20,
    lineHeight: 21,
    letterSpacing: -0.36,
    marginBottom: 20,
  },
  chargeInput: {
    backgroundColor: COLOR.WHITE,
    fontSize: 17,
    fontWeight: "300",
    color: COLOR.GREEN2,
    paddingLeft: "10%",
    height: 60,
    width: "90%",
    shadowColor: "rgba(27, 28, 32, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 30,
    shadowOpacity: 0.2,
    borderRadius: 10,
    alignSelf: "center",
  },
  buttonContainer: {
    flex: 2,
    alignItems: "center",
  },
  button: {
    marginTop: 50,
    width: 150,
    height: 50,
    borderRadius: 100,
    backgroundColor: COLOR.WHITE,
    color: COLOR.BLACK2,
    alignItems: "center",
    justifyContent: "center",

    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: {
      width: 0,
      height: 20,
    },
    shadowRadius: 50,
    shadowOpacity: 0.5,
  },
  buttonText: {
    color: COLOR.GREEN3,
    fontSize: 14,
  },
});

export default NameUpdate;
