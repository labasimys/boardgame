package kr.util;

public class PagingUtil {
	private int startRow;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endRow;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private StringBuffer page;// ������ ǥ�� ���ڿ�

	/**
	 * currentPage : ����������
	 * count : ��ü �Խù� ��
	 * rowCount : �� ��������  �Խù��� ��
	 * pageCount : �� ȭ�鿡 ������ ������ ��
	 * pageUrl : ȣ�� ������ url
	 * addKey : �ΰ����� key ���� ���� null ó�� (&num=23�������� ������ ��)
	 * */
	public PagingUtil(int currentPage, int count, int rowCount,
			int pageCount, String pageUrl) {
		this(null,null,currentPage,count,rowCount,pageCount,pageUrl,null);
	}
	public PagingUtil(int currentPage, int count, int rowCount,
			int pageCount, String pageUrl, String addKey) {
		this(null,null,currentPage,count,rowCount,pageCount,pageUrl,addKey);
	}
	public PagingUtil(String keyfield, String keyword, int currentPage, int count, int rowCount,
			int pageCount,String pageUrl) {
		this(keyfield,keyword,currentPage,count,rowCount,pageCount,pageUrl,null);
	}
	public PagingUtil(String keyfield, String keyword, int currentPage, int count, int rowCount,
			int pageCount,String pageUrl,String addKey) {
		
		String sub_url = "";
		if(keyword != null) sub_url = "&keyfield="+keyfield+"&keyword="+keyword;
		if(addKey != null) sub_url += addKey;
		
		// ��ü ������ ��
		int totalPage = (int) Math.ceil((double) count / rowCount);
		if (totalPage == 0) {
			totalPage = 1;
		}
		// ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		// ���� �������� ó���� ������ ���� ��ȣ ��������.
		startRow = (currentPage - 1) * rowCount + 1;
		endRow = currentPage * rowCount;
		// ���� �������� ������ ������ �� ���ϱ�.
		int startPage = (int) ((currentPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		// ���� block ������
		page = new StringBuffer();
		if (currentPage > pageCount) {
			page.append("<a href="+pageUrl+"?pageNum="+ (startPage - 1) + sub_url +">");
			page.append("<- prev");
			page.append("</a>");
		}
		//������ ��ȣ.���� �������� ���������� �����ϰ� ��ũ�� ����.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				page.append("&nbsp;<b><span style='color:#000; text-decoration:underline;'>");
				page.append(i);
				page.append("</span></b>");
			} else {
				page.append("&nbsp;<a href='"+pageUrl+"?pageNum=");
				page.append(i);
				page.append(sub_url+"'>");
				page.append(i);
				page.append("</a>");
			}
			page.append("&nbsp;");
		}
		// ���� block ������
		if (totalPage - startPage >= pageCount) {
			page.append("<a href="+pageUrl+"?pageNum="+ (endPage + 1) + sub_url +">");
			page.append("next ->");
			page.append("</a>");
		}
	}
	public StringBuffer getPage() {
		return page;
	}
	public int getStartRow() {
		return startRow;
	}
	public int getEndRow() {
		return endRow;
	}
}
