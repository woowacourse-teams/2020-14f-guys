import React, { useState } from "react";
import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import * as ImagePicker from "expo-image-picker";
import { useLinkTo } from "@react-navigation/native";
import { useNavigation } from "@react-navigation/core";

import { raceMissionState } from "../../state/certification/RaceMissionState";
import { COLOR } from "../../utils/constants";
import { loadingState } from "../../state/loading/LoadingState";
import {
  getCameraPermission,
  getCameraRollPermission,
} from "../../utils/Permission";
import PaymentButton from "../home/race/PaymentButton";
import { CertificationApi } from "../../utils/api/CertificationApi";
import { memberTokenState } from "../../state/member/MemberState";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { navigateWithoutHistory } from "../../utils/util";

const CertificationSubmit = ({ route }) => {
  const raceCertifications = useRecoilValue(raceMissionState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();
  const linkTo = useLinkTo();

  const { index } = route.params;
  const raceCertification = raceCertifications[index];
  const [photoUri, setPhotoUri] = useState(
    raceCertification.race.certification_example,
  );

  const submitCertification = async () => {
    setIsLoading(true);
    const formData = new FormData();
    formData.append("certification_image", {
      uri: photoUri,
      type: "image/jpeg",
      name: photoUri.substring(9),
    });
    formData.append("status", "SUCCESS");
    formData.append("description", "필요없을 수도 있는 설명");
    formData.append("riderId", raceCertification.rider.id);
    formData.append("missionId", raceCertification.mission.id);
    try {
      await CertificationApi.post(token, formData);
      alert("인증 완료되었습니다");
      setPhotoUri(raceCertification.race.certification_example);
      navigateWithoutHistory(navigation, "CertificationHome");
      linkTo(`/home/races/detail/${raceCertification.race.id}`);
    } catch (e) {
      alert(e.response.data.message);
    }
    setIsLoading(false);
  };

  const takePhoto = async () => {
    setIsLoading(true);
    const hasCameraPermission = await getCameraPermission();
    const hasCameraRollPermission = await getCameraRollPermission();
    if (hasCameraPermission === false || hasCameraRollPermission === false) {
      setIsLoading(false);
      return;
    }
    const pickerResult = await ImagePicker.launchCameraAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      base64: true,
      quality: 0.1,
    });

    if (pickerResult.cancelled === true) {
      setIsLoading(false);
      return;
    }
    const takenPhoto = pickerResult.uri;
    setPhotoUri(takenPhoto);
    setIsLoading(false);
  };

  const isNotExample = () =>
    photoUri !== raceCertification.race.certification_example;

  return (
    <LoadingIndicator>
      <View style={styles.container}>
        <TouchableOpacity
          style={styles.certificationButton}
          onPress={takePhoto}
        >
          <Image
            source={{ uri: photoUri }}
            style={{
              ...styles.certificationExample,
              opacity: isNotExample() ? 1 : 0.1,
            }}
          />
          <View style={styles.certificationPhoto}>
            <MaterialCommunityIcons
              name="image-plus"
              size={50}
              color={COLOR.GRAY3}
            />
          </View>
        </TouchableOpacity>
        <View style={styles.instructionContainer}>
          <Text style={styles.instruction}>
            {raceCertification.mission.mission_instruction}
          </Text>
        </View>
        {isNotExample() && (
          <View style={styles.absoluteBottom}>
            <PaymentButton onPress={submitCertification} />
          </View>
        )}
      </View>
    </LoadingIndicator>
  );
};

const { width } = Dimensions.get("window");

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  certificationExample: {
    width,
    aspectRatio: 0.8,
    resizeMode: "cover",
  },
  instructionContainer: {
    marginTop: 20,
  },
  instruction: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
    textAlign: "center",
  },
  certificationButton: {
    backgroundColor: COLOR.WHITE,
    width,
  },
  certificationPhoto: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    top: 0,
    justifyContent: "center",
    alignItems: "center",
    opacity: 0.7,
  },
  absoluteBottom: {
    position: "absolute",
    bottom: 0,
  },
});

export default CertificationSubmit;
