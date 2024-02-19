import { useContext } from "react";
import { DiaryDispatchContext, DiaryStateContext } from "./App";
import DiaryItem from "./DiaryItem";

const DiaryList = () => {
  const diaryList = useContext(DiaryStateContext);
  const { onEdit, onRemove } = useContext(DiaryDispatchContext);
  return (
    <div className="DiaryList">
      <h2>일기 리스트</h2>
      <h4>{diaryList.length}개의 일기가 있습니다.</h4>
      <div>
        {diaryList.map((it) => (
          <DiaryItem onEdit={onEdit} key={it.id} {...it} onRemove={onRemove} />
        ))}
      </div>
    </div>
  );
};

DiaryList.defaultProps = {
  diaryList: [],
};

export default DiaryList;
