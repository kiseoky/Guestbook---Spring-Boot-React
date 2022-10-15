import GuestbookDetail from '../components/GuestbookDetail';
import { useParams } from 'react-router-dom';
function DetailPage() {
  const { id } = useParams();
  return (
    <div className="guestbook-detail">
      <GuestbookDetail id={id} />
    </div>
  );
}

export default DetailPage;
