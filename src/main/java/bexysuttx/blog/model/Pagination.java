package bexysuttx.blog.model;

import java.util.ArrayList;
import java.util.List;

public class Pagination extends AbstractModel {
	private final String prevUrl;
	private final String nextUrl;
	private final List<PageItem> pages;

	private Pagination(String prevUrl, String nextUrl, List<PageItem> pages) {
		this.prevUrl = prevUrl;
		this.nextUrl = nextUrl;
		this.pages = pages;
	}

	public boolean isPrev() {
		return prevUrl != null;
	}

	public boolean isNext() {
		return nextUrl != null;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public static final class PageItem {
		private final String url;
		private final String caption;

		private   PageItem(String url, String caption) {
			super();
			this.url = url;
			this.caption = caption;
		}

		public String getUrl() {
			return url;
		}

		public String getCaption() {
			return caption;
		}

		public boolean isEllipsis() {
			return url == null && caption == null;
		}

		public boolean isCurrent() {
			return url == null && caption != null;
		}

		public boolean isPageItem() {
			return url != null && caption != null;
		}

		public static PageItem createEllipsis() {
			return new PageItem(null, null);
		}

		public static PageItem createCurrent(String caption) {
			return new PageItem(null, caption);
		}

		public static PageItem createPageItem(String url, String caption) {
			return new PageItem(url, caption);
		}

	}

	public static class Builder extends AbstractModel {
		private static final int DEFAULT_MAX_PAGINATION_BUTTONS_PER_PAGE = 9;
		private static final int DEFAULT_LIMIT_ITEMS_PER_PAGE = 10;
		private String baseUrl;
		private int offset;
		private int limit;
		private int count;
		private int maxPaginationButtonsPerPage;

		public Builder(String baseUrl, int offset, int count) {
			super();
			this.offset = offset;
			this.count = count;
			this.baseUrl = baseUrl;
			this.limit = DEFAULT_LIMIT_ITEMS_PER_PAGE;
			this.maxPaginationButtonsPerPage = DEFAULT_MAX_PAGINATION_BUTTONS_PER_PAGE;
		}

		public Builder withLimit(int limit) {
			this.limit = limit;
			return this;
		}

		public Builder withmaxPaginationButtonsPerPage(int maxPaginationButtonsPerPage) {
			this.maxPaginationButtonsPerPage = maxPaginationButtonsPerPage;
			return this;
		}

		private List<PageItem> createButtonsWithMiddlePages(int currentPage, int maxPage) {
			List<PageItem> pages = new ArrayList<>();
			pages.add(PageItem.createPageItem(createUrlForPage(1), String.valueOf(1)));
			pages.add(PageItem.createEllipsis());
			int shiftValue = (maxPaginationButtonsPerPage - 5) / 2;
			pages.addAll(createPageItems(currentPage, currentPage - shiftValue, currentPage + shiftValue));
			pages.add(PageItem.createEllipsis());
			pages.add(PageItem.createPageItem(createUrlForPage(maxPage), String.valueOf(maxPage)));
			return pages;

		}

		private List<PageItem> createButtonsWithFirstPageOnly(int currentPage, int maxPage) {
			List<PageItem> pages = new ArrayList<>();
			pages.add(PageItem.createPageItem(createUrlForPage(1), String.valueOf(1)));
			pages.add(PageItem.createEllipsis());
			pages.addAll(createPageItems(currentPage, maxPage - (maxPaginationButtonsPerPage - 3), maxPage));
			return pages;
		}

		private List<PageItem> createButtonsWithLastPageOnly(int currentPage, int maxPage) {
			List<PageItem> pages = createPageItems(currentPage, 1,( maxPaginationButtonsPerPage - 2));
			pages.add(PageItem.createEllipsis());
			pages.add(PageItem.createPageItem(createUrlForPage(maxPage), String.valueOf(maxPage)));
			return pages;
		}

		private List<PageItem> createButtonsOnly(int currentPage, int maxPage) {
			return createPageItems(currentPage, 1, maxPage);
		}

		private List<PageItem> createPageItems(int currentPage, int minPage, int maxPage) {
			List<PageItem> pages = new ArrayList<>();
			for (int page = minPage; page <= maxPage; page++) {
				if (page == currentPage) {
					pages.add(Pagination.PageItem.createCurrent(String.valueOf(currentPage)));
				} else {
					pages.add(Pagination.PageItem.createPageItem(createUrlForPage(page), String.valueOf(page)));
				}
			}
			return pages;

		}

		private String createUrlForPage(int page) {
			if (page > 1) {
				return baseUrl +"page=" +page;
			} else {
				return baseUrl.substring(0, baseUrl.length() - 1);
			}
		}

		private String getNextUrl(int currentPage) {
			if (offset + limit < count) {
				return baseUrl + "page=" + (currentPage + 1);
			} else {
				return null;
			}
		}

		private int getMaxPage() {
			int maxPage = count / limit;
			if (count % limit > 0) {
				maxPage++;
			}
			return maxPage;
		}

		private String getPrevUrl(int currentPage) {
			if (currentPage > 1) {
				return baseUrl + "page=" + (currentPage - 1);
			} else {
				return null;
			}
		}

		public Pagination build() {
			if (count <= limit) {
				return null;
			}

			int currentPage = offset / limit + 1;
			String prevUrl = getPrevUrl(currentPage);
			String nextUrl = getNextUrl(currentPage);
			int maxPage = getMaxPage();
			List<PageItem> pages;
			if (maxPage <= maxPaginationButtonsPerPage) {
				pages = createButtonsOnly(currentPage, maxPage);
			} else {
				int borderValue = (maxPaginationButtonsPerPage - 1) / 2;
				if (currentPage < maxPaginationButtonsPerPage - borderValue) {
					pages = createButtonsWithLastPageOnly(currentPage, maxPage);
				} else if (currentPage > maxPage - (maxPaginationButtonsPerPage - borderValue)) {
					pages = createButtonsWithFirstPageOnly(currentPage, maxPage);
				} else {
					pages = createButtonsWithMiddlePages(currentPage, maxPage);
				}
			}
			return new Pagination(prevUrl, nextUrl, pages);
		}
	}

}
