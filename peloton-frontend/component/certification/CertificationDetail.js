import React, { useEffect } from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import { useRecoilState } from "recoil";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { deviceHeight, deviceWidth } from "../../utils/constants";
import { RiderApi } from "../../utils/api/RiderApi";
import { MissionApi } from "../../utils/api/MissionApi";
import { missionInfoState } from "../../state/mission/MissionState";
import { MemberApi } from "../../utils/api/MemberApi";
import RaceCertificationImage from "../home/race/RaceCertificationImage";
import CertificationRiderInfo from "./CertificationRiderInfo";
import MissionContent from "./MissionContent";
import BorderLine from "./BorderLine";

const CertificationDetail = ({ route }) => {
  const newCertification = route.params.item;
  const setIsLoading = useSetRecoilState(loadingState);
  const [mission, setMission] = useRecoilState(missionInfoState);
  const [member, setMember] = useRecoilState(memberInfoState);
  const token = useRecoilValue(memberTokenState);

  useEffect(() => {
    setIsLoading(true);
    const { mission_id: missionId, rider_id: riderId } = newCertification;
    const fetchData = async () => {
      const newMission = await MissionApi.get(token, missionId);
      const newRider = await RiderApi.get(token, riderId);
      const newMember = await MemberApi.getById(token, newRider.member_id);
      setMission(newMission);
      setMember(newMember);
      setIsLoading(false);
    };
    fetchData().catch((error) => console.log(error));
  }, []);

  return (
    <LoadingIndicator>
      <ScrollView style={styles.container}>
        <View style={styles.certificationImageContainer}>
          {newCertification ? (
            <RaceCertificationImage item={newCertification} touchable={false} />
          ) : null}
        </View>
        {member && newCertification ? (
          <CertificationRiderInfo
            member={member}
            certification={newCertification}
          />
        ) : null}
        <BorderLine />
        {mission ? <MissionContent mission={mission} /> : null}
      </ScrollView>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  certificationImageContainer: {
    width: deviceWidth,
    height: deviceHeight * 0.5,
  },
});

export default CertificationDetail;
