package spring.myapp.shoppingmall.paging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Coupon;
import spring.myapp.shoppingmall.dto.Reply;
import spring.myapp.shoppingmall.dto.goods;

@Service
public class Paging 
{
	@Autowired
	private MallDao Malldao;
	
    private final static int pageCount = 5;  //pageCount = 6;
    private final static int shopPage = 6;
    private int blockStartNum = 0;
    private int blockLastNum = 0;
    private int lastPageNum = 0;

    public int getBlockStartNum() {
        return blockStartNum;
    }
    public void setBlockStartNum(int blockStartNum) {
        this.blockStartNum = blockStartNum;
    }
    public int getBlockLastNum() {
        return blockLastNum;
    }
    public void setBlockLastNum(int blockLastNum) {
        this.blockLastNum = blockLastNum;
    }
    public int getLastPageNum() {
        return lastPageNum;
    }
    public void setLastPageNum(int lastPageNum) {
        this.lastPageNum = lastPageNum;
    }

    // block�� ����
    // ���� �������� ���� block�� ���� ��ȣ,�� ��ȣ�� ���
    public void makeBlock(int curPage){		//pageCount�� �� ���� ������ �� �ִ� �ִ��� ������ �� 
        int blockNum = 0;
        blockNum = (int)Math.floor((curPage-1)/ pageCount);
        blockStartNum = (pageCount * blockNum) + 1;					//����� ���� ����
        blockLastNum = blockStartNum + (pageCount-1);				//����� �� ����
    }

    public List<goods> dtos(int curPageNum,String kind)
    {
    	List<goods> list = null;									
    	list = Malldao.getCurPageDtos(curPageNum,kind);					//���� �������� �ش��ϴ� ��ǰ ����Ʈ��
    	
    	return list;
    }
    
    public List<goods> dtoWithKwd(int curPageNum,String search)
    {
    	List<goods> list = null;
    	list = Malldao.getsearchinfo(curPageNum, search);
    	
    	return list;
    }
    
    public int lastPageNumKwd(String search) 
    {
    	int total = Malldao.getCountKwd(search);
    	
    	if( total % shopPage == 0 ) {
            return lastPageNum = (int)Math.floor(total/shopPage);			//������ �������� ��1
         }
         else {
            return lastPageNum = (int)Math.floor(total/shopPage) + 1;		//������ �������� ��2
         }
    }
    public int lastPageNum(String kind) 						//������ ������ �ѹ�
    {
    	int total = Malldao.getCount(kind); 					//�� ��ǰ ������ �� DB�� ��ϵǾ� �ִ� ����  
    	
    	if( total % shopPage == 0 ) {
           return lastPageNum = (int)Math.floor(total/shopPage);			//������ �������� ��1
        }
        else {
           return lastPageNum = (int)Math.floor(total/shopPage) + 1;		//������ �������� ��2
        }
    }
    
    
	public int lastPageNumOrder(String id) {
		int total = Malldao.getCountOrder(id); 					//�� ��ǰ ������ �� DB�� ��ϵǾ� �ִ� ����  
    	
    	if( total % shopPage == 0 ) {
           return lastPageNum = (int)Math.floor(total/shopPage);			//������ �������� ��1
        }
        else {
           return lastPageNum = (int)Math.floor(total/shopPage) + 1;		//������ �������� ��2
        }
		
	}
	public int lastPageNumCoupon(String id) {
		int total = Malldao.getNumberCoupons(id); 					//�� ��ǰ ������ �� DB�� ��ϵǾ� �ִ� ����  
    	
    	if( total % shopPage == 0 ) {
           return lastPageNum = (int)Math.floor(total/shopPage);			//������ �������� ��1
        }
        else {
           return lastPageNum = (int)Math.floor(total/shopPage) + 1;		//������ �������� ��2
        }
	}
	public List<Coupon> coupons(int curPageNum, String id) {
		List<Coupon> list = null;									
    	list = Malldao.getCurPageCoupons(curPageNum,id);					//���� �������� �ش��ϴ� ��ǰ ����Ʈ��
    	
    	return list;
	}
	
	public int lastPageNumReply(String gId) 
	{
		int total = Malldao.getNumberReply(gId); 					//�� ��ǰ ������ �� DB�� ��ϵǾ� �ִ� ����  

		if( total % shopPage == 0 ) {
           return lastPageNum = (int)Math.floor(total/shopPage);			//������ �������� ��1
        }
        else {
           return lastPageNum = (int)Math.floor(total/shopPage) + 1;		//������ �������� ��2
        }
	}
	
	public List<Reply> reply(int curPageNum, String gId) {
		List<Reply> list = null;									
    	list = Malldao.getCurPageReplies(curPageNum,gId);					//���� �������� �ش��ϴ� ��ǰ ����Ʈ��
    	
    	return list;
	}
    
    /*
    // �˻��� ���� �� �� �������� ������ ��ȣ
    public void makeLastPageNum(String kwd,String bBoard) 
    {  
        int total = Malldao.getCount(kwd,bBoard);
        System.out.println("total:"+total);

        if( total % pageCount == 0 ) 
        {
            lastPageNum = (int)Math.floor(total/pageCount);
        }
        else 
        {
            lastPageNum = (int)Math.floor(total/pageCount) + 1;
        }
    }
    */
}