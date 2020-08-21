import React, { useEffect } from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { memberTokenState } from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { deviceHeight, deviceWidth } from "../../utils/constants";
import { MissionApi } from "../../utils/api/MissionApi";
import { MemberApi } from "../../utils/api/MemberApi";
import RaceCertificationImage from "../home/race/RaceCertificationImage";
import CertificationRiderInfo from "./CertificationRiderInfo";
import MissionContent from "./MissionContent";
import BorderLine from "./BorderLine";
import {
  certificationDetailState,
  certificationsState,
} from "../../state/certification/CertificationState";
import { ridersInfoState } from "../../state/rider/RiderState";

const CertificationDetail = ({ route }) => {
  const certificationId = route.params.id;
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const ridersInfo = useRecoilValue(ridersInfoState);
  const certificationsInfo = useRecoilValue(certificationsState);
  const [certificationDetail, setCertificationDetail] = useRecoilState(
    certificationDetailState
  );

  useEffect(() => {
    setIsLoading(true);
    let certificationInfo = {
      certification: null,
      mission: null,
      member: null,
    };
    const fetchData = async () => {
      const certification = certificationsInfo.filter(
        (item) => item.id === certificationId
      )[0];
      certificationInfo = {
        ...certificationInfo,
        certification,
      };
      const { mission_id: missionId, rider_id: riderId } = certification;

      try {
        const mission = await MissionApi.get(token, missionId);
        certificationInfo = {
          ...certificationInfo,
          mission,
        };
      } catch (e) {
        console.log(e.response.data.message);
        alert(e.response.data.code);
        setIsLoading(false);
        return;
      }
      try {
        const rider = ridersInfo.filter((item) => item.id === riderId)[0];
        const member = await MemberApi.getById(token, rider.member_id);
        certificationInfo = {
          ...certificationInfo,
          member,
        };
      } catch (e) {
        console.log(e.response.data.message);
        alert(e.response.data.code);
        setIsLoading(false);
        return;
      }
      setCertificationDetail(certificationInfo);
      setIsLoading(false);
    };
    fetchData();
  }, []);

  return (
    certificationDetail && (
      <LoadingIndicator>
        <ScrollView style={styles.container}>
          <View style={styles.certificationImageContainer}>
            {certificationDetail.certification && (
              <RaceCertificationImage
                item={certificationDetail.certification}
                touchable={false}
              />
            )}
          </View>
          <View style={styles.infoContainer}>
            {certificationDetail.member &&
              certificationDetail.certification && (
                <CertificationRiderInfo
                  member={certificationDetail.member}
                  certification={certificationDetail.certification}
                />
              )}
            <BorderLine />
          </View>
          {certificationDetail.mission && (
            <MissionContent mission={certificationDetail.mission} />
          )}
        </ScrollView>
      </LoadingIndicator>
    )
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  infoContainer: {
    marginHorizontal: 10,
  },
  certificationImageContainer: {
    width: deviceWidth,
    height: deviceHeight * 0.5,
  },
});

export default CertificationDetail;
